package com.example.jpablog.board.repository;

import com.example.jpablog.board.entity.BoardBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardBookmarkRepository extends JpaRepository<BoardBookmark, Long>{

}
