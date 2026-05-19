package com.example.demo.domain.project.dto.request;

import com.example.demo.domain.project.entity.ProjectStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // RequestBody 역직렬화를 위해 필요
public class ProjectUpdateReqDto {

    private String farmName;
    private String productCategory;
    private String thumbnailImageUrl;
    private String description;
    private ProjectStatus status;
    private String happyBeanUrl;
}
