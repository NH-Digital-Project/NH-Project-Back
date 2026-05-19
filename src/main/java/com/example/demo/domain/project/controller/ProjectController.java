package com.example.demo.domain.project.controller;

import com.example.demo.domain.project.dto.request.ProjectCreateReqDto;
import com.example.demo.domain.project.service.ProjectService;
import com.example.demo.global.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createProject(
            @Valid @RequestBody ProjectCreateReqDto request
    ) {
        projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.successWithMessage("선정업체가 등록되었습니다."));
    }
}
