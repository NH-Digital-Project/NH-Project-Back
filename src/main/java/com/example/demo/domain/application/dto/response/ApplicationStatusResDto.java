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
public class ApplicationStatusResDto {
    private final Long applicationId;
    private final String applicationNumber;
    private final ApplicationStatus status;
    private final String name;
    private final String businessName;
    private final String mainProduct;
    private final LocalDateTime createdAt;

    public static ApplicationStatusResDto from(Application application) {
        return ApplicationStatusResDto.builder()
                .applicationId(application.getId())
                .applicationNumber(application.getApplicationNumber())
                .status(application.getStatus())
                .name(application.getUserName())
                .businessName(application.getBusinessName())
                .mainProduct(application.getMainProduct())
                .createdAt(application.getCreatedAt())
                .build();
    }
}
