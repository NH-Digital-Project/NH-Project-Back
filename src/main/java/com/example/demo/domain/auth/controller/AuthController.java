package com.example.demo.domain.auth.controller;

import com.example.demo.domain.auth.dto.request.AdminLoginReqDto;
import com.example.demo.domain.auth.dto.response.AdminLoginResDto;
import com.example.demo.domain.auth.service.AuthService;
import com.example.demo.global.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
    private static final int REFRESH_TOKEN_MAX_AGE = 7 * 24 * 60 * 60; // 7일
    private static final String COOKIE_PATH = "/";
    private static final String SAME_SITE_POLICY = "Lax";

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AdminLoginResDto>> adminLogin(
        @Valid @RequestBody AdminLoginReqDto reqDto) {

        AdminLoginResDto resDto = authService.adminLogin(reqDto);
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME,
                resDto.refreshToken())
            .httpOnly(true)
            .secure(true) // 로컬에서는 false 설정
            .path(COOKIE_PATH)
            .maxAge(REFRESH_TOKEN_MAX_AGE)
            .sameSite(SAME_SITE_POLICY)
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(ApiResponse.success(resDto));
    }


}
