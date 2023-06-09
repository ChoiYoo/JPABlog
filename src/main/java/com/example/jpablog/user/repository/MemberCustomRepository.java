package com.example.jpablog.user.repository;

import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.model.MemberLogCount;
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

    public List<MemberLogCount> findMemberLogCount() {
        String sql = "select m.id, m.email, m.user_name" +
                ", (select count(*) from notice n where n.member_id = m.id) notice_count " +
                ", (select count(*) from notice_like nl where nl.member_id = m.id) notice_like_count " +
                "from member m";

        List<MemberLogCount> list = em.createNativeQuery(sql).getResultList();
        return list;
    }

    public List<MemberLogCount> findMemberLikeBest() {
        String sql = "select t1.id, t1.email, t1.user_name, t1.notice_like_count " +
                "from (select m.*, (select count(*) from notice_like nl where nl.member_id = m.id) " +
                "as notice_like_count from member m) t1 order by t1.notice_like_count desc";

        List<MemberLogCount> list = em.createNativeQuery(sql).getResultList();
        return list;

    }
}