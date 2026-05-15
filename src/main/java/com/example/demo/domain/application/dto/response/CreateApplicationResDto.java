package com.example.demo.domain.application.dto.response;

import com.example.demo.domain.application.entity.Application;
import com.example.demo.domain.application.status.ApplicationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateApplicationResDto {
    private Long applicationId;
    private String applicationNumber;
    private Long userId;
    private ApplicationStatus status;
    private LocalDateTime createdAt;

    public static CreateApplicationResDto from(Application application) {
        return CreateApplicationResDto.builder()
                .applicationId(application.getId())
                .applicationNumber(application.getApplicationNumber())
                .userId(application.getUser().getId())
                .status(application.getStatus())
                .createdAt(application.getCreatedAt())
                .build();
    }
}
