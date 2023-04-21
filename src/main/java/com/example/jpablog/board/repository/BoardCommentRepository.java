package com.example.jpablog.board.repository;

import com.example.jpablog.board.entity.BoardComment;
import com.example.jpablog.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {

    List<BoardComment> findByMember(Member member);
}
