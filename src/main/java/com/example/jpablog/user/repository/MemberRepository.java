package com.example.jpablog.user.repository;

import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.model.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
