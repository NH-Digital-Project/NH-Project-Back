package com.example.demo.domain.application.dto.response;

import com.example.demo.domain.application.entity.Application;
import com.example.demo.domain.application.status.ApplicationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class CreateApplicationResDto {
    private final Long applicationId;
    private final String applicationNumber;
    private final Long userId;
    private final ApplicationStatus status;
    private final LocalDateTime createdAt;

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
