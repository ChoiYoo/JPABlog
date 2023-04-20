package com.example.jpablog.board.service;

import com.example.jpablog.board.entity.BoardBadReport;
import com.example.jpablog.board.entity.BoardType;
import com.example.jpablog.board.model.*;

import java.util.List;

public interface BoardService {

    ServiceResult addBoard(BoardTypeInput boardTypeInput);

    ServiceResult updateBoard(long id, BoardTypeInput boardTypeInput);

    ServiceResult deleteBoard(Long id);

    List<BoardType> getAllBoardType();

    ServiceResult setBoardTypeUsing(Long id, BoardTypeUsing boardTypeUsing);

    List<BoardTypeCount> getBoardTypeCount();

    ServiceResult setBoardTop(Long id, boolean topYn);

    ServiceResult setBoardPeriod(Long id, BoardPeriod boardPeriod);

    ServiceResult setBoardHits(Long id, String email);

    ServiceResult setBoardLikes(Long id, String email);

    ServiceResult setBoardUnLikes(Long id, String email);

    ServiceResult addBadReport(Long id, String email, BoardBadReportInput boardBadReportInput);

    List<BoardBadReport> badReportList();

    ServiceResult scrapBoard(Long id, String email);
}
