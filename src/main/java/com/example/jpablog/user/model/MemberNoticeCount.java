package com.example.jpablog.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class MemberNoticeCount {

    private long id;
    private String email;
    private String userName;

    private long noticeCount;
}
