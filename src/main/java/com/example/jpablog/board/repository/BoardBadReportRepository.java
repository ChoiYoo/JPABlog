package com.example.jpablog.board.repository;

import com.example.jpablog.board.entity.Board;
import com.example.jpablog.board.entity.BoardBadReport;
import com.example.jpablog.board.entity.BoardHits;
import com.example.jpablog.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardBadReportRepository extends JpaRepository<BoardBadReport, Long>{

}
