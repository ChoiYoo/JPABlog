package com.example.jpablog.user.controller;

import com.example.jpablog.notice.entity.Notice;
import com.example.jpablog.notice.model.NoticeResponse;
import com.example.jpablog.notice.model.ResponseError;
import com.example.jpablog.notice.repository.NoticeRepository;
import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.exception.ExistEmailException;
import com.example.jpablog.user.exception.MemberNotFoundException;
import com.example.jpablog.user.exception.PasswordNotMatchException;
import com.example.jpablog.user.model.MemberInput;
import com.example.jpablog.user.model.MemberInputPassword;
import com.example.jpablog.user.model.MemberReponse;
import com.example.jpablog.user.model.MemberUpdate;
import com.example.jpablog.user.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ApiMemberController {

    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;


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

//    @PostMapping("/api/user")
//    public ResponseEntity<?> addUser(@RequestBody @Valid MemberInput memberInput, Errors errors) {
//
//        List<ResponseError> responseErrorList = new ArrayList<>();
//        if (errors.hasErrors()) {
//            errors.getAllErrors().forEach((e) -> {
//                responseErrorList.add(ResponseError.of((FieldError)e));
//            });
//            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
//        }
//
//        Member user = Member.builder()
//                .email(memberInput.getEmail())
//                .userName(memberInput.getUsername())
//                .password(memberInput.getPassword())
//                .phone(memberInput.getPhone())
//                .regDate(LocalDateTime.now())
//                .build();
//
//        memberRepository.save(user);
//
//        return ResponseEntity.ok().build();
//    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> MemberNotFoundExceptionHandler(MemberNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid MemberUpdate memberUpdate, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError)e));
            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("사용자 정보가 없습니다."));

        member.setPhone(memberUpdate.getPhone());
        member.setUpdateDate(LocalDateTime.now());
        memberRepository.save(member);

        return ResponseEntity.ok().build();


    }

    @GetMapping("/api/user/{id}")
    public MemberReponse getMember(@PathVariable Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("사용자 정보가 없습니다."));

//        MemberReponse memberReponse = new MemberReponse(member);
        MemberReponse memberReponse = MemberReponse.of(member);
        return memberReponse;

    }

    @GetMapping("/api/user/{id}/notice")
    public List<NoticeResponse> MemberNotice(@PathVariable Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("사용자 정보가 없습니다."));

        List<Notice> noticeList = noticeRepository.findByMember(member);

        List<NoticeResponse> noticeResponsesList = new ArrayList<>();

        noticeList.stream().forEach((e) -> {
            noticeResponsesList.add(NoticeResponse.of(e));
        });

        return noticeResponsesList;
    }

    @PostMapping("/api/user")
    public ResponseEntity<?> AddUser(@RequestBody @Valid MemberInput memberInput, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError)e));
            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        if (memberRepository.countByEmail(memberInput.getEmail()) > 0 ) {
            throw new ExistEmailException("이미 가입된 이메일입니다.");
        }

        Member member = Member.builder()
                .email(memberInput.getEmail())
                .userName(memberInput.getUsername())
                .phone(memberInput.getPhone())
                .password(memberInput.getPassword())
                .regDate(LocalDateTime.now())
                .build();
        memberRepository.save(member);

        return ResponseEntity.ok().build();

    }

    @ExceptionHandler(value = {ExistEmailException.class, PasswordNotMatchException.class})
    public ResponseEntity<?> ExistEmailExceptionHandler(RuntimeException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @PatchMapping("/api/user/{id}/password")
    public ResponseEntity<?> updateMemberPassword(@PathVariable Long id, @RequestBody MemberInputPassword memberInputPassword, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError)e));
            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        Member member = memberRepository.findByIdAndPassword(id, memberInputPassword.getPassword())
                .orElseThrow(() -> new PasswordNotMatchException("비밀번호가 일치하지 않습니다."));

        member.setPassword(memberInputPassword.getNewPassword());

        memberRepository.save(member);

        return ResponseEntity.ok().build();
    }
}

