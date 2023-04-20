package com.example.jpablog.board.repository;

import com.example.jpablog.board.model.BoardTypeCount;
import com.example.jpablog.user.model.MemberLogCount;
import com.example.jpablog.user.model.MemberNoticeCount;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class BoardTypeCustomRepository {

    private final EntityManager em;

    public List<BoardTypeCount> getBoardTypeCount(){
        String sql = "select bt.id, bt.board_name, bt.reg_date, bt.using_yn" +
                ", (select count(*) from board b where b.board_type_id = bt.id) as board_count" +
                " from board_type bt";


        List<Object[]> result = em.createNativeQuery(sql).getResultList();
        List<BoardTypeCount> resultList = result.stream().map(e -> new BoardTypeCount(e))
                .collect(Collectors.toList());

        return resultList;
    }
}