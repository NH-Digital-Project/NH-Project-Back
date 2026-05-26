package com.example.demo.domain.application.controller;

import com.example.demo.domain.application.dto.request.ApplicationReqDto;
import com.example.demo.domain.application.dto.response.ApplicationStatusResDto;
import com.example.demo.domain.application.dto.response.ApplicationResDto;
import com.example.demo.domain.application.dto.response.CreateApplicationResDto;
import com.example.demo.domain.application.service.ApplicationService;
import com.example.demo.global.common.dto.ApiResponse;
import com.example.demo.global.security.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateApplicationResDto>> createApplication(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @Valid @RequestBody ApplicationReqDto request
            ) {
        CreateApplicationResDto response = applicationService.createApplication(principalDetails.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<ApplicationResDto>> getMyApplication(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ApplicationResDto response = applicationService.getMyApplication(principalDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/me/status")
    public ResponseEntity<ApiResponse<ApplicationStatusResDto>>  getMyApplicationStatus(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ApplicationStatusResDto response = applicationService.getMyApplicationStatus(principalDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<String>> deleteMyApplication(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        applicationService.deleteMyApplication(principalDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.successWithMessage("지원이 취소되었습니다."));
    }
}
