package com.example.demo.domain.user.controller;

import com.example.demo.domain.user.dto.response.MyInfoResponse;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.global.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<MyInfoResponse>> getMyInfo() {
        // Todo 하드코딩 제거 필요
        return ResponseEntity.ok(ApiResponse.success(userService.getMyInfo(1L)));
    }
}
