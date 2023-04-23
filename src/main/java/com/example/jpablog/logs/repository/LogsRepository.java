package com.example.jpablog.logs.repository;

import com.example.jpablog.logs.entity.Logs;
import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.model.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Long> {
}
