package com.example.jpablog.board.service;

import com.example.jpablog.board.entity.BoardType;
import com.example.jpablog.board.model.BoardTypeCount;
import com.example.jpablog.board.model.BoardTypeInput;
import com.example.jpablog.board.model.BoardTypeUsing;
import com.example.jpablog.board.model.ServiceResult;

import java.util.List;

public interface BoardService {

    ServiceResult addBoard(BoardTypeInput boardTypeInput);

    ServiceResult updateBoard(long id, BoardTypeInput boardTypeInput);

    ServiceResult deleteBoard(Long id);

    List<BoardType> getAllBoardType();

    ServiceResult setBoardTypeUsing(Long id, BoardTypeUsing boardTypeUsing);

    List<BoardTypeCount> getBoardTypeCount();

    ServiceResult setBoardTop(Long id, boolean topYn);
}
