package com.example.demo.domain.project.dto.request;

import com.example.demo.domain.project.entity.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProjectCreateReqDto {

    @NotNull(message = "지원서 ID는 필수입니다.")
    private final Long applicationId;

    @NotBlank(message = "농장명은 필수입니다.")
    private final String farmName;

    @NotBlank(message = "품목은 필수입니다.")
    private final String productCategory;

    private final String thumbnailImageUrl;

    @NotBlank(message = "소개글은 필수입니다.")
    private final String description;

    @NotNull(message = "진행 상태값은 필수입니다.")
    private final ProjectStatus status;

    @NotBlank(message = "해피빈 URL은 필수입니다.")
    private final String happyBeanUrl;
}
