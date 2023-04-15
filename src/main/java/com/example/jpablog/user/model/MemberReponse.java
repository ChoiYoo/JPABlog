package com.example.jpablog.user.model;

import com.example.jpablog.user.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberReponse {

    private Long id;
    private String email;
    private String userName;
    private String phone;

//    public MemberReponse(Member member) {
//        this.id = member.getId();
//        this.email = member.getEmail();
//        this.userName = member.getUserName();
//        this.phone = member.getPhone();
//    }

    public static MemberReponse of(Member member) {
        return MemberReponse.builder()
                .id(member.getId())
                .userName(member.getUserName())
                .phone(member.getPhone())
                .email(member.getEmail())
                .build();
    }
}
