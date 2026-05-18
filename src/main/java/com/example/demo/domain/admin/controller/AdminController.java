package com.example.demo.domain.admin.controller;

import com.example.demo.domain.admin.dto.request.AdminCreateReqDto;
import com.example.demo.domain.admin.dto.response.AdminCreateResDto;
import com.example.demo.domain.admin.dto.response.AdminListResDto;
import com.example.demo.domain.admin.service.AdminService;
import com.example.demo.global.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        @Valid @RequestBody AdminCreateReqDto createReqDto
    ){

        return ResponseEntity.ok(ApiResponse.success(adminService.createAdmin(createReqDto)));


    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<ApiResponse<String>> deleteAdmin(
        @PathVariable Long adminId
    ){
        adminService.deleteAdmin(adminId);
        // 현재 ApiResponse에서 성공시 데이터만 넘기고 있는데 메시지를 data로 넘기는게 어색한데 ApiResponse에 메시지 필드를 추가하는건 어떤지?
        return ResponseEntity.ok(ApiResponse.successWithMessage("계정정보가 삭제되었습니다."));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<AdminListResDto>> getAdmins(){
        return ResponseEntity.ok(ApiResponse.success(adminService.getAdmins()));
    }
}
