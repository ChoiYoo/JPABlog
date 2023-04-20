package com.example.jpablog.board.repository;

import com.example.jpablog.board.entity.Board;
import com.example.jpablog.board.entity.BoardHits;
import com.example.jpablog.board.entity.BoardLikes;
import com.example.jpablog.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardLikesRepository extends JpaRepository<BoardLikes, Long>{

    long countByBoardAndMember(Board board, Member member);

    Optional<BoardLikes> findByBoardAndMember(Board board, Member member);
}
