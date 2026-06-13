package com.example.demo.domain.project.dto.request;

import com.example.demo.domain.project.entity.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdateReqDto {

    @NotBlank(message = "사업자명은 필수입니다.")
    private String businessName;

    @NotBlank(message = "품목은 필수입니다.")
    private String productCategory;

    private String thumbnailImageUrl;

    @NotBlank(message = "소개글은 필수입니다.")
    private String description;

    private ProjectStatus status;

    @Pattern(regexp = "^(http(s)?://.+)?$", message = "올바른 URL 형식이 아닙니다.")
    private String happyBeanUrl;
}
