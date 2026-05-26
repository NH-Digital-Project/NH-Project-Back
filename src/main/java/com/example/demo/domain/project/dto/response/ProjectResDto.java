package com.example.demo.domain.project.dto.response;

import com.example.demo.domain.project.entity.Project;
import com.example.demo.domain.project.entity.ProjectStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ProjectResDto {
    private final Long projectId;
    private final String farmName;
    private final String productCategory;
    private final String description;
    private final ProjectStatus status;
    private final String thumbnailImageUrl;
    private final String happyBeanUrl;

    public static ProjectResDto from(Project project) {
        return ProjectResDto.builder()
                .projectId(project.getId())
                .farmName(project.getFarmName())
                .productCategory(project.getProductCategory())
                .description(project.getDescription())
                .status(project.getProjectStatus())
                .thumbnailImageUrl(project.getThumbnailImageUrl())
                .happyBeanUrl(project.getHappyBeanUrl())
                .build();
    }
}
