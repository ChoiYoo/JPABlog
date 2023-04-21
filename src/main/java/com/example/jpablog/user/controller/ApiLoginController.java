package com.example.jpablog.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.jpablog.board.entity.Board;
import com.example.jpablog.board.entity.BoardComment;
import com.example.jpablog.board.model.ServiceResult;
import com.example.jpablog.board.service.BoardService;
import com.example.jpablog.common.exception.BizException;
import com.example.jpablog.common.model.ResponseResult;
import com.example.jpablog.notice.entity.Notice;
import com.example.jpablog.notice.entity.NoticeLike;
import com.example.jpablog.notice.model.NoticeResponse;
import com.example.jpablog.notice.model.ResponseError;
import com.example.jpablog.notice.repository.NoticeLikeRepository;
import com.example.jpablog.notice.repository.NoticeRepository;
import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.exception.ExistEmailException;
import com.example.jpablog.user.exception.MemberNotFoundException;
import com.example.jpablog.user.exception.PasswordNotMatchException;
import com.example.jpablog.user.model.*;
import com.example.jpablog.user.repository.MemberRepository;
import com.example.jpablog.user.service.MemberService;
import com.example.jpablog.user.service.PointService;
import com.example.jpablog.util.JWTUtils;
import com.example.jpablog.util.PasswordUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ApiLoginController {

    private final MemberService memberService;

    /**
     * 83. 회원 로그인 히스토리 기능을 구현하는 API를 작성해 보세요.
     */
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody @Valid MemberLogin memberLogin, Errors errors){

        if(errors.hasErrors()){
            return ResponseResult.fail("입력 값이 정확하지 않습니다.", ResponseError.of(errors.getAllErrors()));
        }
        Member member = null;
        try {
            member = memberService.login(memberLogin);
        } catch (BizException e) {
            return ResponseResult.fail(e.getMessage());
        }
        MemberLoginToken memberLoginToken = JWTUtils.createToken(member);

        if (memberLoginToken == null){
            return ResponseResult.fail("JWT 생성에 실패하였습니다.");
        }

        return ResponseResult.success(memberLoginToken);
    }
}

