package com.example.demo.domain.application.controller;

import com.example.demo.domain.application.dto.request.ApplicationReqDto;
import com.example.demo.domain.application.dto.response.ApplicationStatusResDto;
import com.example.demo.domain.application.dto.response.ApplicationResDto;
import com.example.demo.domain.application.dto.response.CreateApplicationResDto;
import com.example.demo.domain.application.service.ApplicationService;
import com.example.demo.global.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    // TODO: Security 도입 후 userId추출 로직 작성
    private final Long USER_ID = 1L;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateApplicationResDto>> createApplication(
            @Valid @RequestBody ApplicationReqDto request
            ) {
        CreateApplicationResDto response = applicationService.createApplication(USER_ID, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<ApplicationResDto>> getMyApplication() {
        ApplicationResDto response = applicationService.getMyApplication(USER_ID);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/me/status")
    public ResponseEntity<ApiResponse<ApplicationStatusResDto>>  getMyApplicationStatus() {
        ApplicationStatusResDto response = applicationService.getMyApplicationStatus(USER_ID);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
