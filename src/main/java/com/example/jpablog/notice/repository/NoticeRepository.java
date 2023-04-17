package com.example.jpablog.notice.repository;

import com.example.jpablog.notice.entity.Notice;
import com.example.jpablog.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//interface는 기본적으로 JpaRepository 상속받음
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Optional<List<Notice>> findByIdIn(List<Long> idList);

    // 제목동일, 내용동일, 등록시간이 체크시간보다 크다.

    Optional<List<Notice>> findByTitleAndContentsAndRegDateIsGreaterThanEqual(String title, String contents, LocalDateTime regDate);

    int countByTitleAndContentsAndRegDateIsGreaterThanEqual(String title, String contents, LocalDateTime regDate);

    List<Notice> findByMember(Member member);

    long countByMember(Member member);
}
