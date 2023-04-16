package com.example.jpablog.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class MemberInputPassword {

    @NotBlank(message = "현재 비밀번호 필수 항목입니다.")
    private String password;

    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하여야 합니다.")
    @NotBlank(message = "신규 비밀번호는 필수 항목입니다.")
    private String newPassword;
}
