package com.example.demo.domain.project.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectOrderUpdateReqDto {
    @NotNull(message = "프로젝트 ID는 필수입니다.")
    private Long projectId;

    @NotNull(message = "정렬 순서는 필수입니다.")
    private Integer sortOrder;
}