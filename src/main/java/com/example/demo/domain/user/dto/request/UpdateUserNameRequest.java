package com.example.demo.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserNameRequest(

    @NotBlank(message = "이름은 필수입니다.")
    String userName
) {

}
