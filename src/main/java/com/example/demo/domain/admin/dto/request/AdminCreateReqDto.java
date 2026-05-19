package com.example.demo.domain.admin.dto.request;


import com.example.demo.domain.admin.entity.Admin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AdminCreateReqDto {
    @NotBlank(message = "아이디는 필수값입니다.")
    private final String adminLoginId;

    @NotBlank(message = "관리자 이름은 필수값입니다.")
    private final String adminName;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    private final String password;

    public Admin toEntity(){
        return Admin.builder()
                   .loginId(adminLoginId)
                   .adminName(adminName)
                   .password(password)
                   .build();
    }
}
