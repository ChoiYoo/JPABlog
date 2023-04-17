package com.example.jpablog.user.controller;

import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.model.MemberReponse;
import com.example.jpablog.user.model.ResponseMessage;
import com.example.jpablog.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiAdminUserController {

    private final MemberRepository memberRepository;

    @GetMapping("/api/admin/user")
    public ResponseMessage memberList(){

        List<Member> memberList = memberRepository.findAll();
        long totalMemberCount = memberRepository.count();

        return ResponseMessage.builder()
                .totalCount(totalMemberCount)
                .data(memberList)
                .build();

    }
}
