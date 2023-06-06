package com.example.jpablog.board.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.jpablog.board.entity.BoardBadReport;
import com.example.jpablog.board.entity.BoardType;
import com.example.jpablog.board.model.*;
import com.example.jpablog.board.service.BoardService;
import com.example.jpablog.common.model.ResponseResult;
import com.example.jpablog.notice.model.ResponseError;
import com.example.jpablog.user.model.ResponseMessage;
import com.example.jpablog.util.JWTUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiAdminBoardController {

    private final BoardService boardService;

    /**
     * 74. 게시글의 신고하기 목록을 조회하는 API를 작성해 보세요.
     */
    @GetMapping("/api/admin/board/badreport")
    public ResponseEntity<?> badReport(){
        List<BoardBadReport> list = boardService.badReportList();
        return ResponseResult.success(list);
    }

    /**
     98. 문의 게시판이 글에 답변을 달았을때 메일로 답변 정보를 전송하는 API를 작성해 보세요.
     */
    @PostMapping("/api/admin/board/{id}/reply")
    public ResponseEntity<?> reply(@PathVariable Long id, @RequestBody BoardReplyInput boardReplyInput) {

        ServiceResult result = boardService.replyBoard(id, boardReplyInput);
        return ResponseResult.result(result);

    }
}
