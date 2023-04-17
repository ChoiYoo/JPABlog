package com.example.jpablog.user.controller;

import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.model.MemberReponse;
import com.example.jpablog.user.model.ResponseMessage;
import com.example.jpablog.user.model.UserSearch;
import com.example.jpablog.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ApiAdminUserController {

    private final MemberRepository memberRepository;

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
}
