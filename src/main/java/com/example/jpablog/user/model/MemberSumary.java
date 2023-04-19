package com.example.jpablog.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class MemberSumary {

    private long stopMemberCount;
    private long usingMemberCount;
    private long totalMemberCount;
}
