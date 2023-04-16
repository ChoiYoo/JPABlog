package com.example.jpablog.user.controller;

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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ApiMemberController {

    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeLikeRepository noticeLikeRepository;


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

//    @PostMapping("/api/user")
//    public ResponseEntity<?> AddUser(@RequestBody @Valid MemberInput memberInput, Errors errors) {
//
//        List<ResponseError> responseErrorList = new ArrayList<>();
//        if (errors.hasErrors()) {
//            errors.getAllErrors().stream().forEach((e) -> {
//                responseErrorList.add(ResponseError.of((FieldError)e));
//            });
//            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
//        }
//
//        if (memberRepository.countByEmail(memberInput.getEmail()) > 0 ) {
//            throw new ExistEmailException("이미 가입된 이메일입니다.");
//        }
//
//        Member member = Member.builder()
//                .email(memberInput.getEmail())
//                .userName(memberInput.getUsername())
//                .phone(memberInput.getPhone())
//                .password(memberInput.getPassword())
//                .regDate(LocalDateTime.now())
//                .build();
//        memberRepository.save(member);
//
//        return ResponseEntity.ok().build();
//
//    }

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

    private String getEncryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
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

        String encryptPassword = getEncryptPassword(memberInput.getPassword());

        Member member = Member.builder()
                .email(memberInput.getEmail())
                .userName(memberInput.getUsername())
                .phone(memberInput.getPhone())
                .password(encryptPassword)
                .regDate(LocalDateTime.now())
                .build();
        memberRepository.save(member);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("api/user/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("사용자 정보가 없습니다."));

        // 유저가 쓴 공지사항이 있는 경우
        // -> ??
        // 1. 삭제가 안된다. 삭제하려면, 본인이 쓴 공지사항 모조리 삭제
        // 2. 유저 삭제에 공지사항 글을 다 삭제하는 경우
        // 3. 유저는 삭제하되, 공지사항 글은 남겨두는 경우

        try {
            memberRepository.delete(member);
        } catch (DataIntegrityViolationException e) {
            String message = "제약조건에 문제가 발생하였습니다.";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            String message = "회원탈퇴 중 문제가 발생하였습니다.";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> findMember(@RequestBody MemberInputFind memberInputFind) {

        Member member = memberRepository.findByUserNameAndPhone(memberInputFind.getUserName(), memberInputFind.getPhone())
                .orElseThrow(() -> new MemberNotFoundException("사용자 정보가 없습니다."));

        MemberReponse memberReponse = MemberReponse.of(member);

        return ResponseEntity.ok().body(memberReponse);
    }

    private String getResetPassword() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
    }

    @GetMapping ("/api/user/{id}/password/reset")
    public ResponseEntity<?> resetMemberPassword(@PathVariable Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("사용자 정보가 없습니다."));

        // 비밀번호 초기화
        String resetPassword = getResetPassword();
        String resetEncryptPassword = getEncryptPassword(resetPassword);

        member.setPassword(resetEncryptPassword);
        memberRepository.save(member);

        String message = String.format("[%s]님의 임시비밀번호가 [%s]로 초기화되었습니다."
        , member.getUserName()
        , resetPassword);
        sendSMS(message);

        return ResponseEntity.ok().build();

    }

    void sendSMS(String message) {
        System.out.println("[문자메시지전송]");
        System.out.println(message);

    }

    @GetMapping("api/user/{id}/notice/like")
    public List<NoticeLike> likeNotice(@PathVariable Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("사용자 정보가 없습니다."));

        List<NoticeLike> noticeLikeList = noticeLikeRepository.findByMember(member);

        return noticeLikeList;


    }
}

