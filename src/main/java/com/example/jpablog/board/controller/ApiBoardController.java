package com.example.jpablog.board.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.jpablog.board.entity.BoardType;
import com.example.jpablog.board.model.*;
import com.example.jpablog.board.service.BoardService;
import com.example.jpablog.common.model.ResponseResult;
import com.example.jpablog.notice.entity.Notice;
import com.example.jpablog.notice.exception.AlreadyDeletedException;
import com.example.jpablog.notice.exception.DuplicateNoticeException;
import com.example.jpablog.notice.exception.NoticeNotFoundException;
import com.example.jpablog.notice.model.NoticeDeleteInput;
import com.example.jpablog.notice.model.NoticeInput;
import com.example.jpablog.notice.model.NoticeModel;
import com.example.jpablog.notice.model.ResponseError;
import com.example.jpablog.notice.repository.NoticeRepository;
import com.example.jpablog.user.model.ResponseMessage;
import com.example.jpablog.util.JWTUtils;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ApiBoardController {

    private final BoardService boardService;

    /**
     * 61. 게시판타입을 추가하는 API를 작성해 보세요.
     * - 동일한 게시판 제목이 있는 경우 status:200, result:false, message에 이미 동일한 게시판이 존재한다는 메시지 리턴
     * - 게시판이름은 필수항목에 대한 부분 체크
     */
    @PostMapping("/api/board/type")
    public ResponseEntity<?> addBoardType(@RequestBody @Valid BoardTypeInput boardTypeInput, Errors errors){

        if (errors.hasErrors()) {
            List<ResponseError> responseErrors = ResponseError.of(errors.getAllErrors());
            return new ResponseEntity<>(ResponseMessage.fail("입력 값이 정확하지 않습니다.", responseErrors), HttpStatus.BAD_REQUEST);
        }

        ServiceResult result = boardService.addBoard(boardTypeInput);
        if (!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }

        return ResponseEntity.ok().build();
    }

    /**
     * 62. 게시판타입명을 수정하는 API를 작성해 보세요.
     * - 게시판명이 동일할 경우 "수정할 이름이 동일한 게시판명 입니다." 리턴
     */
    @PutMapping("/api/board/type/{id}")
    public ResponseEntity<?> updateBoardType(@PathVariable Long id, @RequestBody @Valid BoardTypeInput boardTypeInput, Errors errors) {

        if(errors.hasErrors()){
            List<ResponseError> responseErrors = ResponseError.of(errors.getAllErrors());
            return new ResponseEntity<>(ResponseMessage.fail("입력 값이 정확하지 않습니다.", responseErrors), HttpStatus.BAD_REQUEST);
        }

        ServiceResult result = boardService.updateBoard(id, boardTypeInput);
        if (!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }

        return ResponseEntity.ok().build();
    }

    /**
     * 63. 게시판타입 삭제하는 API를 작성해 보세요.
     * - 삭제시는 게시글이 있는 경우 삭제 안됨
     */
    @DeleteMapping("/api/board/type/{id}")
    public ResponseEntity<?> deleteBoardType(@PathVariable Long id) {

        ServiceResult result = boardService.deleteBoard(id);
        if(!result.isResult()){
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    /**
     * 64. 게시판타입의 목록을 리턴하는 API를 작성해 보세요.
     */
    @GetMapping("/api/board/type")
    public ResponseEntity<?> boardType() {

        List<BoardType> boardTypeList = boardService.getAllBoardType();

        return ResponseEntity.ok().body(ResponseMessage.success(boardTypeList));
    }

    /**
     * 65. 게시판타입의 사용여부를 설정하는 API를 작성해 보세요.
     */
    @PatchMapping("/api/board/type/{id}/using")
    public ResponseEntity<?> usingBoardType(@PathVariable Long id, @RequestBody BoardTypeUsing boardTypeUsing){

        ServiceResult result = boardService.setBoardTypeUsing(id, boardTypeUsing);
        if (!result.isResult()){
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }

        return ResponseEntity.ok().body(ResponseMessage.success());

    }

    /**
     * 66. 게시판별 작성된 게시글의 개수를 리턴하는 API를 작성해 보세요.
     * - 현재 사용가능한 게시판에 대해서 게시글의 개수를 리턴함
     */
    @GetMapping("/api/board/type/count")
    public ResponseEntity<?> boardTypeCount(){

        List<BoardTypeCount> list = boardService.getBoardTypeCount();
        return ResponseEntity.ok().body(list);

    }

    /**
     * 67. 게시된 게시글을 최상단에 배치하는 API를 작성해 보세요.
     */
    @PatchMapping ("/api/board/{id}/top")
    public ResponseEntity<?> boardPostTop(@PathVariable Long id){

        ServiceResult result = boardService.setBoardTop(id, true);
        return ResponseEntity.ok().body(result);
    }

    /**
     * 68. 최상단에 게시된 게시글을 최상단에서 해제하는 API를 작성해 보세요.
     */
    @PatchMapping ("/api/board/{id}/top/clear")
    public ResponseEntity<?> boardPostTopClear(@PathVariable Long id){

        ServiceResult result = boardService.setBoardTop(id, false);
        return ResponseEntity.ok().body(result);
    }

    /**
     * 69. 게시글의 게시기간을 시작일과 종료일로 설정하는 API를 작성해 보세요.
     */
    @PatchMapping("/api/board/{id}/publish")
    public ResponseEntity<?> boardPeriod(@PathVariable Long id, @RequestBody BoardPeriod boardPeriod){

        ServiceResult result = boardService.setBoardPeriod(id, boardPeriod);

        if(!result.isResult()){
            return ResponseResult.fail(result.getMessage());
        }
        return ResponseResult.success();
    }

    /**
     * 70. 게시글의 조회수를 증가시키는 API를 작성해 보세요. 다만, 동일사용자 게시글 조회수 증가를 방지하는 부분에 대한 로직도 구현해 보세요.
     * - JWT 인증을 통과한 사용자에 대해서 진행
     */
    @PutMapping("/api/board/{id}/hits")
    public ResponseEntity<?> boardHits(@PathVariable Long id, @RequestHeader("JWT-TOKEN") String token){

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        ServiceResult result = boardService.setBoardHits(id, email);
        if(result.isFail()){
            return ResponseResult.fail(result.getMessage());
        }
        return ResponseResult.success();

    }
}
