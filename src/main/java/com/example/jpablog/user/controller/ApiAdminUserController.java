package com.example.jpablog.user.controller;

import com.example.jpablog.notice.repository.NoticeRepository;
import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.entity.MemberLoginHistory;
import com.example.jpablog.user.exception.MemberNotFoundException;
import com.example.jpablog.user.model.MemberReponse;
import com.example.jpablog.user.model.MemberStatusInput;
import com.example.jpablog.user.model.ResponseMessage;
import com.example.jpablog.user.model.UserSearch;
import com.example.jpablog.user.repository.MemberLoginHistoryRepository;
import com.example.jpablog.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ApiAdminUserController {

    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;
    private final MemberLoginHistoryRepository memberLoginHistoryRepository;

    /*
    @GetMapping("/api/admin/user")
    public ResponseMessage memberList(){

        List<Member> memberList = memberRepository.findAll();
        long totalMemberCount = memberRepository.count();

        return ResponseMessage.builder()
                .totalCount(totalMemberCount)
                .data(memberList)
                .build();
    }

     */
    @GetMapping("/api/admin/user/{id}")
    public ResponseEntity<?> memberDetail(@PathVariable Long id) {

        Optional<Member> member = memberRepository.findById(id);
        if (!member.isPresent()) {

            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body(ResponseMessage.success(member));
    }

    @GetMapping("/api/admin/user/search")
    public ResponseEntity<?> findMember(@RequestBody UserSearch userSearch) {

        List<Member> memberList =
        memberRepository.findByEmailContainsOrPasswordContainsOrUserNameContains(userSearch.getEmail()
                , userSearch.getPhone()
                , userSearch.getUsername());

        return ResponseEntity.ok().body(ResponseMessage.success(memberList));
    }

    @PatchMapping("/api/admin/user/{id}/status")
    public ResponseEntity<?> MemberStatus(@PathVariable Long id, @RequestBody MemberStatusInput memberStatusInput){

        Optional<Member> optionalMember = memberRepository.findById(id);

        if(!optionalMember.isPresent()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 없습니다."), HttpStatus.BAD_REQUEST);
        }
        Member member = optionalMember.get();

        member.setStatus(memberStatusInput.getStatus());
        memberRepository.save(member);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/admin/user/{id}")
    public ResponseEntity<?> DeleteMember(@PathVariable Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);

        if(!optionalMember.isPresent()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 없습니다."), HttpStatus.BAD_REQUEST);
        }

        Member member = optionalMember.get();

        if (noticeRepository.countByMember(member) > 0) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자가 작성한 공지사항이 있습니다."), HttpStatus.BAD_REQUEST);
        }

        memberRepository.delete(member);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/api/admin/user/login/history")
    public ResponseEntity<?> MemberLoginHistory(){

        List<MemberLoginHistory> memberLoginHistoryList = memberLoginHistoryRepository.findAll();

        return ResponseEntity.ok().body(memberLoginHistoryList);
    }
}
