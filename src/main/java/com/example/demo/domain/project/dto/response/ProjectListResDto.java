package com.example.demo.domain.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProjectListResDto {
    private final List<ProjectResDto> projects;
}
