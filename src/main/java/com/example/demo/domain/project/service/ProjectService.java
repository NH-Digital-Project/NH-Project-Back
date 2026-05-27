package com.example.demo.domain.project.service;

import com.example.demo.domain.admin.repository.AdminRepository;
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
import com.example.demo.global.common.service.S3Service;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ApplicationRepository applicationRepository;
    private final AdminRepository adminRepository;
    private final S3Service s3Service;

    private void validateAdmin(Long adminId) {
        if (!adminRepository.existsById(adminId)) {
            throw new CustomException(ErrorCode.ADMIN_UNAUTHORIZED);
        }
    }

    @Transactional
    public void createProject(Long adminId, ProjectCreateReqDto request, MultipartFile thumbnail) {
        validateAdmin(adminId);

        // 이미 등록된 지원서인지 중복 검증
        if (projectRepository.existsByApplicationId(request.getApplicationId())) {
            throw new CustomException(ErrorCode.PROJECT_ALREADY_EXISTS);
        }

        // 지원서 조회
        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new CustomException(ErrorCode.APPLICATION_NOT_FOUND));

        application.approve();

        // S3 업로드 및 URL 반환
        String s3ImageUrl = s3Service.uploadFile(thumbnail);

        Project project = Project.builder()
                .application(application)
                .farmName(request.getFarmName())
                .productCategory(request.getProductCategory())
                .thumbnailImageUrl(s3ImageUrl)
                .description(request.getDescription())
                .projectStatus(request.getStatus())
                .happyBeanUrl(request.getHappyBeanUrl())
                .build();

        projectRepository.save(project);
    }

    @Transactional
    public void updateProject(Long adminId, Long projectId, ProjectUpdateReqDto request, MultipartFile thumbnail) {
        validateAdmin(adminId);

        // 수정할 선정업체 조회
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

        // 이미지가 새로 들어왔다면 기존 이미지 삭제 후 새 이미지 업로드
        String imageUrl = project.getThumbnailImageUrl();
        if (thumbnail != null && !thumbnail.isEmpty()) {
            String newImageUrl = s3Service.uploadFile(thumbnail);

            // 기존에 등록되어 있던 이미지가 있다면 S3에서 삭제
            if (imageUrl != null) {
                s3Service.deleteFile(imageUrl);
            }

            // 엔티티에 넘겨줄 주소를 새 S3 주소로 교체
            imageUrl = newImageUrl;
        }

        project.update(
                request.getFarmName(),
                request.getProductCategory(),
                imageUrl, // 새로 갱신된 URL 전달
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
    public void deleteProject(Long adminId, Long projectId) {
        validateAdmin(adminId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

        // 선정업체와 연결된 지원서 상태 변경
        if(project.getApplication() != null) {
            project.getApplication().submit();
        }

        projectRepository.delete(project);
        projectRepository.flush(); // DB에서 삭제 및 제약조건 위반 검증을 먼저 수행

        // DB 삭제가 완전히 성공한 이후에 S3 파일을 삭제
        if (project.getThumbnailImageUrl() != null) {
            s3Service.deleteFile(project.getThumbnailImageUrl());
        }
    }

    // 드래그 앤 드롭 다중 순서 업데이트 로직
    @Transactional
    public void updateProjectOrders(Long adminId, List<ProjectOrderUpdateReqDto> orderRequests) {
        validateAdmin(adminId);

        List<Long> projectIds = orderRequests.stream()
                .map(ProjectOrderUpdateReqDto::getProjectId)
                .toList();

        Map<Long, Project> projectMap = projectRepository.findAllById(projectIds).stream()
                .collect(Collectors.toMap(Project::getId, project -> project));

        for (ProjectOrderUpdateReqDto request : orderRequests) {
            Project project = projectMap.get(request.getProjectId());
            if (project == null) {
                throw new CustomException(ErrorCode.PROJECT_NOT_FOUND);
            }
            project.updateSortOrder(request.getSortOrder());
        }
    }
}
