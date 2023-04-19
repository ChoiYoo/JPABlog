package com.example.jpablog.user.controller;

import com.example.jpablog.notice.repository.NoticeRepository;
import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.entity.MemberLoginHistory;
import com.example.jpablog.user.model.*;
import com.example.jpablog.user.repository.MemberLoginHistoryRepository;
import com.example.jpablog.user.repository.MemberRepository;
import com.example.jpablog.user.service.MemberService;
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

    private final MemberService memberService;

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

    @PatchMapping("/api/admin/user/{id}/lock")
    public ResponseEntity<?> MemberLock(@PathVariable Long id) {

        Optional<Member> optionalMember = memberRepository.findById(id);

        if(!optionalMember.isPresent()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 없습니다."), HttpStatus.BAD_REQUEST);
        }

        Member member = optionalMember.get();

        if(member.isLockYn()) {
            return new ResponseEntity<>(ResponseMessage.fail("이미 접속제한이 된 사용자입니다."), HttpStatus.BAD_REQUEST);
        }
        member.setLockYn(true);
        memberRepository.save(member);

        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @PatchMapping("/api/admin/user/{id}/unlock")
    public ResponseEntity<?> MemberUnLock(@PathVariable Long id) {

        Optional<Member> optionalMember = memberRepository.findById(id);

        if(!optionalMember.isPresent()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 없습니다."), HttpStatus.BAD_REQUEST);
        }

        Member member = optionalMember.get();

        if(!member.isLockYn()) {
            return new ResponseEntity<>(ResponseMessage.fail("이미 접속제한이 해제된 사용자입니다."), HttpStatus.BAD_REQUEST);
        }
        member.setLockYn(false);
        memberRepository.save(member);

        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @GetMapping("/api/admin/user/status/count")
    public ResponseEntity<?> MemberStatusCount(){

//        memberRepository.countByStatus(MemberStatus.Using);
//        memberRepository.countByStatus(MemberStatus.Stop);

        MemberSumary memberSumary = memberService.getMemberStatusCount();

        return ResponseEntity.ok().body(ResponseMessage.success(memberSumary));

    }

    @GetMapping("/api/admin/user/today")
    public ResponseEntity<?> todayMember() {

        List<Member> members = memberService.getTodayMembers();

        return ResponseEntity.ok().body(ResponseMessage.success(members));

    }

    @GetMapping("/api/admin/user/notice/count")
    public ResponseEntity<?> memberNoticeCount() {

        List<MemberNoticeCount> memberNoticeCountList = memberService.getMemberNoticeCount();

        return ResponseEntity.ok().body(ResponseMessage.success(memberNoticeCountList));

    }


    @GetMapping("/api/admin/user/log/count")
    public ResponseEntity<?> memberLogCount() {

        List<MemberLogCount> memberLogCounts = memberService.getMemberLogCount();

        return ResponseEntity.ok().body(ResponseMessage.success(memberLogCounts));

    }

    @GetMapping("/api/admin/user/like/best")
    public ResponseEntity<?> bestLikeCount() {

        List<MemberLogCount> memberLogCounts = memberService.getMemberLikeBest();

        return ResponseEntity.ok().body(ResponseMessage.success(memberLogCounts));
    }
}
