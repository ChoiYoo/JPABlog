package com.example.jpablog.user.repository;

import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.model.MemberStatus;
import com.example.jpablog.user.model.MemberSumary;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    int countByEmail(String email);

    Optional<Member> findByIdAndPassword(Long id, String password);

    Optional<Member> findByUserNameAndPhone(String username, String phone);

    Optional<Member> findByEmail(String email);

    List<Member> findByEmailContainsOrPasswordContainsOrUserNameContains(String email, String phone, String username);

    long countByStatus(MemberStatus memberStatus);


    @Query("select m from Member m where m.regDate between :startDate and :endDate ")
    List<Member> findToday(LocalDateTime startDate, LocalDateTime endDate);
}
