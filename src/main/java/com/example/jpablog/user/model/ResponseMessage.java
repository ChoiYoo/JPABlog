package com.example.jpablog.user.model;

import com.example.jpablog.user.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class ResponseMessage {

    private long totalCount;
    private List<Member> data;
}
