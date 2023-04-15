package com.example.jpablog.user.controller;

import com.example.jpablog.notice.model.ResponseError;
import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.model.MemberInput;
import com.example.jpablog.user.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiMemberController {

    private final MemberRepository memberRepository;


//    @PostMapping("/api/user")
//    public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors) {
//
//        List<ResponseError> responseErrorList = new ArrayList<>();
//
//        if (errors.hasErrors()) {
//
//            errors.getAllErrors().forEach((e) -> {
//                responseErrorList.add(ResponseError.of((FieldError)e));
//            });
//
//            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
//        }
//
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/api/user")
    public ResponseEntity<?> addUser(@RequestBody @Valid MemberInput memberInput, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError)e));
            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        Member user = Member.builder()
                .email(memberInput.getEmail())
                .userName(memberInput.getUsername())
                .password(memberInput.getPassword())
                .phone(memberInput.getPhone())
                .regDate(LocalDateTime.now())
                .build();

        memberRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
