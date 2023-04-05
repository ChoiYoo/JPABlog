package com.example.jpablog.notice.repository;

import com.example.jpablog.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//interface는 기본적으로 JpaRepository 상속받음
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
