package com.example.jpablog.board.entity;

import com.example.jpablog.user.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BoardBadReport {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 신고자 정보
    @Column
    private long memberId;
    @Column
    private String userName;
    @Column
    private String userEmail;

    // 신고게시물정보
    @Column
    private long boardId;
    @Column
    private long boardMemberId;
    @Column
    private String boardTitle;
    @Column
    private String boardContents;
    @Column
    private LocalDateTime boardRegDate;

    @Column
    private String comments;
    @Column
    private LocalDateTime regDate;
}
