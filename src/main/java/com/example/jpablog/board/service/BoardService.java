package com.example.jpablog.board.service;

import com.example.jpablog.board.entity.BoardType;
import com.example.jpablog.board.model.BoardTypeInput;
import com.example.jpablog.board.model.ServiceResult;

public interface BoardService {

    ServiceResult addBoard(BoardTypeInput boardTypeInput);

    ServiceResult updateBoard(long id, BoardTypeInput boardTypeInput);
}
