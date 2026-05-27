package com.example.demo.domain.project.controller;

import com.example.demo.domain.project.dto.request.ProjectCreateReqDto;
import com.example.demo.domain.project.dto.request.ProjectOrderListReqDto;
import com.example.demo.domain.project.dto.request.ProjectUpdateReqDto;
import com.example.demo.domain.project.dto.response.ProjectListResDto;
import com.example.demo.domain.project.entity.ProjectStatus;
import com.example.demo.domain.project.service.ProjectService;
import com.example.demo.global.common.dto.ApiResponse;
import com.example.demo.global.security.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> createProject(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @Valid @RequestPart(value = "request") ProjectCreateReqDto request,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail
    ) {
        projectService.createProject(principalDetails.getUserId(), request, thumbnail);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.successWithMessage("선정업체가 등록되었습니다."));
    }

    @PatchMapping(value = "/{projectId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> updateProject(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long projectId,
            @Valid @RequestPart(value = "request") ProjectUpdateReqDto request,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail
    ) {
        projectService.updateProject(principalDetails.getUserId(), projectId, request, thumbnail);
        return ResponseEntity.ok(ApiResponse.successWithMessage("정보가 수정되었습니다."));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ProjectListResDto>> getAllProjects(
            @RequestParam(required = false) ProjectStatus status
    ) {
        ProjectListResDto response = projectService.getProjects(status);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<ApiResponse<String>> deleteProject(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long projectId
    ) {
        projectService.deleteProject(principalDetails.getUserId(), projectId);
        return ResponseEntity.ok(ApiResponse.successWithMessage("선정업체가 삭제되었습니다."));
    }

    @PatchMapping("/orders")
    public ResponseEntity<ApiResponse<String>> updateProjectOrders(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @Valid @RequestBody ProjectOrderListReqDto request
    ) {
        projectService.updateProjectOrders(principalDetails.getUserId(), request.getOrders());
        return ResponseEntity.ok(ApiResponse.successWithMessage("선정업체 노출 순서가 변경되었습니다."));
    }
}
