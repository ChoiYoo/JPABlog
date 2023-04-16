package com.example.jpablog.notice.repository;

import com.example.jpablog.notice.entity.Notice;
import com.example.jpablog.notice.entity.NoticeLike;
import com.example.jpablog.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeLikeRepository extends JpaRepository<NoticeLike, Long> {

    List<NoticeLike> findByMember(Member member);
}
