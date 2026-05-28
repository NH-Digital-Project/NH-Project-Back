package com.example.demo.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdatePhoneNumberRequest(

    @NotBlank(message = "연락처는 필수입니다.")
    String phoneNumber
) {

}
