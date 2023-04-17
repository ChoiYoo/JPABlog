package com.example.jpablog.user.repository;

import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.entity.MemberLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberLoginHistoryRepository extends JpaRepository<MemberLoginHistory, Long> {



}
