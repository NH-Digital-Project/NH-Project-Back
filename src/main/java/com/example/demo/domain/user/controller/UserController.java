package com.example.demo.domain.user.controller;


import com.example.demo.domain.user.dto.response.MyInfoResponse;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.global.common.dto.ApiResponse;
import com.example.demo.global.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<ApiResponse<MyInfoResponse>> getMyInfo(@AuthenticationPrincipal
    PrincipalDetails principalDetails) {
        return ResponseEntity.ok(
            ApiResponse.success(userService.getMyInfo(principalDetails.getUserId())));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteMe(@AuthenticationPrincipal
    PrincipalDetails principalDetails) {
        userService.deleteMe(principalDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success("탈퇴되었습니다."));

    }

}
