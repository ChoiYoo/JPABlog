package com.example.jpablog.user.model;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class MemberLogin {

    @NotBlank(message = "이메일 항목은 필수입니다.")
    private String email;

    @NotBlank(message = "비밀번호 항목은 필수입니다.")
    private String password;

}
