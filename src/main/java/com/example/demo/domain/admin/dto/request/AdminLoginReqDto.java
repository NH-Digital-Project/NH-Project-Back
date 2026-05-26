package com.example.demo.domain.admin.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AdminLoginReqDto(

    @NotBlank(message = "id는 필수입니다.")
    String adminLoginId,

    // Todo 비밀번호 입력 조건 필요 시 추가
    @NotBlank(message = "비밀번호는 필수입니다.")
    String password
) {

}
