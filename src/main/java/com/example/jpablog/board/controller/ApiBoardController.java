package com.example.jpablog.board.controller;

import com.example.jpablog.board.entity.BoardType;
import com.example.jpablog.board.model.BoardTypeInput;
import com.example.jpablog.board.model.ServiceResult;
import com.example.jpablog.board.service.BoardService;
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
}
