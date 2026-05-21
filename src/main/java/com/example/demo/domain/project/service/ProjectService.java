package com.example.demo.domain.project.service;

import com.example.demo.domain.application.entity.Application;
import com.example.demo.domain.application.repository.ApplicationRepository;
import com.example.demo.domain.project.dto.request.ProjectCreateReqDto;
import com.example.demo.domain.project.dto.request.ProjectUpdateReqDto;
import com.example.demo.domain.project.entity.Project;
import com.example.demo.domain.project.repository.ProjectRepository;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public void createProject(ProjectCreateReqDto request) {
        // 이미 등록된 지원서인지 중복 검증
        if (projectRepository.existsByApplicationId(request.getApplicationId())) {
            throw new CustomException(ErrorCode.PROJECT_ALREADY_EXISTS);
        }

        // 지원서 조회
        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new CustomException(ErrorCode.APPLICATION_NOT_FOUND));

        application.approve();

        Project project = Project.builder()
                .application(application)
                .farmName(request.getFarmName())
                .productCategory(request.getProductCategory())
                .thumbnailImageUrl(request.getThumbnailImageUrl())
                .description(request.getDescription())
                .projectStatus(request.getStatus())
                .happyBeanUrl(request.getHappyBeanUrl())
                .build();

        projectRepository.save(project);
    }

    @Transactional
    public void updateProject(Long projectId, ProjectUpdateReqDto request) {
        // 수정할 선정업체 조회
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

        project.update(
                request.getFarmName(),
                request.getProductCategory(),
                request.getThumbnailImageUrl(),
                request.getDescription(),
                request.getStatus(),
                request.getHappyBeanUrl()
        );
    }
}
