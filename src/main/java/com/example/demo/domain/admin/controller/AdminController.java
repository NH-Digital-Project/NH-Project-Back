package com.example.demo.domain.admin.controller;

import com.example.demo.domain.admin.dto.request.AdminCreateReqDto;
import com.example.demo.domain.admin.dto.response.AdminCreateResDto;
import com.example.demo.domain.admin.service.AdminService;
import com.example.demo.global.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<ApiResponse<AdminCreateResDto>> createAdmin(
        //Todo: 사용자(관리자) 검증 필요
        @RequestBody AdminCreateReqDto createReqDto
    ){

        return ResponseEntity.ok(ApiResponse.success(adminService.createAdmin(createReqDto)));


    }
}
