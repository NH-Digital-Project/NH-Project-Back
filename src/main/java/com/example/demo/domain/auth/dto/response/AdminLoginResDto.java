package com.example.demo.domain.auth.dto.response;

public record AdminLoginResDto(
    String accessToken,
    String refreshToken
) {

}
