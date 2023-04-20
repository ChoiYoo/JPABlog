package com.example.jpablog.board.repository;

import com.example.jpablog.board.entity.Board;
import com.example.jpablog.board.entity.BoardBadReport;
import com.example.jpablog.board.entity.BoardScrap;
import com.example.jpablog.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardScrapRepository extends JpaRepository<BoardScrap, Long>{

}
