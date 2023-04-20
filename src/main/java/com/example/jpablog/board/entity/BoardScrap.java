package com.example.jpablog.board.entity;

import com.example.jpablog.user.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity @Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BoardScrap {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private Member member;

    @Column
    private long boardId;
    @Column
    private long boardTypeId;
    @Column
    private long boardMemberId;
    @Column
    private String boardTitle;
    @Column
    private String boardContents;
    @Column
    private LocalDateTime boardRegDate;

    @Column
    private LocalDateTime regDate;
}
