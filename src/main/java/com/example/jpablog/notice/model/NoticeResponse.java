package com.example.jpablog.notice.model;

import com.example.jpablog.notice.entity.Notice;
import com.example.jpablog.user.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class NoticeResponse {

    private long id;

    private long regMemberId;
    private String regUserName;

    private String title;
    private String contents;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private int hits;
    private int likes;

    public static NoticeResponse of(Notice notice) {
        return NoticeResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .contents(notice.getContents())
                .regDate(notice.getRegDate())
                .regMemberId(notice.getMember().getId())
                .regUserName(notice.getMember().getUserName())
                .updateDate(notice.getUpdateDate())
                .hits(notice.getHits())
                .likes(notice.getLikes())
                .build();
    }
}
