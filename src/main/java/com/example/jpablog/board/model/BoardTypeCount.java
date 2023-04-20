package com.example.jpablog.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BoardTypeCount {

    private Long id;
    private String boardName;
    private LocalDateTime regDate;
    private boolean usingYn;
    private Long boardCount;


    public BoardTypeCount(Object[] arrObj) {
        this.id = (Long)arrObj[0];
        this.boardName = (String)arrObj[1];
        this.regDate = ((Timestamp)arrObj[2]).toLocalDateTime();
        this.usingYn = (Boolean)arrObj[3];
        this.boardCount = (Long)arrObj[4];
    }
}
