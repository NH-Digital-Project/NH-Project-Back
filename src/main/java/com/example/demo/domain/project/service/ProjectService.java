package com.example.demo.domain.project.service;

import com.example.demo.domain.application.entity.Application;
import com.example.demo.domain.application.repository.ApplicationRepository;
import com.example.demo.domain.project.dto.request.ProjectCreateReqDto;
import com.example.demo.domain.project.dto.request.ProjectOrderUpdateReqDto;
import com.example.demo.domain.project.dto.request.ProjectUpdateReqDto;
import com.example.demo.domain.project.dto.response.ProjectListResDto;
import com.example.demo.domain.project.dto.response.ProjectResDto;
import com.example.demo.domain.project.entity.Project;
import com.example.demo.domain.project.entity.ProjectStatus;
import com.example.demo.domain.project.repository.ProjectRepository;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public ProjectListResDto getProjects(ProjectStatus status) {
        List<Project> projects;

        if(status != null) {
            projects = projectRepository.findByProjectStatusOrderBySortOrderAscCreatedAtAsc(status);
        } else {
            projects = projectRepository.findAllWithCustomOrder();
        }

        List<ProjectResDto> projectDtos = projects.stream()
                .map(ProjectResDto::from)
                .toList();

        return new ProjectListResDto(projectDtos);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

        // 선정업체와 연결된 지원서 상태 변경
        if(project.getApplication() != null) {
            project.getApplication().submit();
        }

        projectRepository.delete(project);
    }

    // 드래그 앤 드롭 다중 순서 업데이트 로직
    @Transactional
    public void updateProjectOrders(List<ProjectOrderUpdateReqDto> orderRequests) {
        for (ProjectOrderUpdateReqDto request : orderRequests) {
            Project project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));
            project.updateSortOrder(request.getSortOrder());
        }
    }
}
