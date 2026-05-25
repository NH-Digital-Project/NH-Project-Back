package com.example.demo.domain.admin.controller;

import com.example.demo.domain.admin.dto.request.AdminCreateReqDto;
import com.example.demo.domain.admin.dto.request.AdminLoginReqDto;
import com.example.demo.domain.admin.dto.response.AdminCreateResDto;
import com.example.demo.domain.admin.dto.response.AdminListResDto;
import com.example.demo.domain.admin.dto.response.AdminLoginResDto;
import com.example.demo.domain.admin.dto.response.ApplicationListResDto;
import com.example.demo.domain.admin.dto.response.UserListResDto;
import com.example.demo.domain.admin.service.AdminService;
import com.example.demo.global.common.dto.ApiResponse;
import com.example.demo.global.security.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<ApiResponse<AdminCreateResDto>> createAdmin(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @Valid @RequestBody AdminCreateReqDto createReqDto
    ) {

        return ResponseEntity.ok(ApiResponse.success(adminService.createAdmin(principalDetails.getUserId(),createReqDto)));


    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<ApiResponse<String>> deleteAdmin(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long adminId
    ) {
        adminService.deleteAdmin(principalDetails.getUserId(), adminId);
        return ResponseEntity.ok(ApiResponse.successWithMessage("계정정보가 삭제되었습니다."));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<AdminListResDto>> getAdmins(
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAdmins(
            principalDetails.getUserId())));
    }


    // 관리자가 지원자(지원서)의 목록을 조회하는 API
    @GetMapping("/applications")
    public ResponseEntity<ApiResponse<ApplicationListResDto>> getApplications(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable,
        @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(
            ApiResponse.success(adminService.getApplications(principalDetails.getUserId(), pageable, keyword)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AdminLoginResDto>> adminLogin(
        @Valid @RequestBody AdminLoginReqDto reqDto) {
        return ResponseEntity.ok(ApiResponse.success(adminService.adminLogin(reqDto)));
    }
  
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<UserListResDto>> getUsers(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PageableDefault(size = 10 , sort = "createdAt" , direction = Direction.DESC) Pageable pageable,
        @RequestParam(required = false) String keyword
    ){
        return ResponseEntity.ok(
            ApiResponse.success(adminService.getUsers(principalDetails.getUserId(), pageable, keyword))
        );
    }
}
