package com.example.demo.domain.project.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProjectOrderListReqDto {
    @Valid
    @NotNull(message = "순서 목록은 필수입니다.")
    private List<ProjectOrderUpdateReqDto> orders;
}