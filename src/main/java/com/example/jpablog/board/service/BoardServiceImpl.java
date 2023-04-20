package com.example.jpablog.board.service;

import com.example.jpablog.board.entity.BoardType;
import com.example.jpablog.board.model.BoardTypeInput;
import com.example.jpablog.board.model.ServiceResult;
import com.example.jpablog.board.repository.BoardTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardTypeRepository boardTypeRepository;
    @Override
    public ServiceResult addBoard(BoardTypeInput boardTypeInput) {

        BoardType boardType = boardTypeRepository.findByBoardName(boardTypeInput.getName());
        if(boardType != null && boardTypeInput.getName().equals(boardType.getBoardName())) {
            //동일한 게시판 제목이 있는 것
            return ServiceResult.fail("이미 동일한 게시판이 존재합니다.");
        }

        BoardType addBoardType = BoardType.builder()
                .boardName(boardTypeInput.getName())
                .regDate(LocalDateTime.now())
                .build();

        boardTypeRepository.save(addBoardType);

        return ServiceResult.success();
    }
}
