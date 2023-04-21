package com.example.jpablog.user.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.jpablog.board.model.ServiceResult;
import com.example.jpablog.common.model.ResponseResult;
import com.example.jpablog.user.service.MemberService;
import com.example.jpablog.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ApiMemberInterestController {

    private final MemberService memberService;

    /**
     * 78. 관심사용자에 등록하는 API를 작성해 보세요.
     */
    @PutMapping("/api/user/{id}/interest")
    public ResponseEntity<?> interestMember(@PathVariable Long id,
                               @RequestHeader("JWT-TOKEN") String token){

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        }catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 올바르지 않습니다.");
        }

        ServiceResult result = memberService.addInterestMember(email, id);
        return ResponseResult.result(result);
    }

}

