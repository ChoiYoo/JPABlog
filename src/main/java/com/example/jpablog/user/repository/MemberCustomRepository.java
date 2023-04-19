package com.example.jpablog.user.repository;

import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.model.MemberNoticeCount;
import com.example.jpablog.user.model.MemberStatus;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberCustomRepository{

    private final EntityManager em;

    public List<MemberNoticeCount> findMemberNoticeCount() {

        String sql = "select m.id, m.email, m.user_name, (select count(*) from Notice n where n.member_id = m.id) notice_count from Member m";

        List<MemberNoticeCount> list = em.createNativeQuery(sql).getResultList();
        return list;

    }
}
