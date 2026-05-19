package com.example.demo.domain.admin.dto.response;

import com.example.demo.domain.application.entity.Application;
import com.example.demo.domain.application.status.ApplicationStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
public class ApplicationSummaryDto {

    private final String applicationNumber;
    private final LocalDateTime createdAt;
    private final String userName;
    private final String farmName;
    private final String productCategory;
    private final ApplicationStatus status;

    public static ApplicationSummaryDto from(Application application){
        return ApplicationSummaryDto.builder()
                   .applicationNumber(application.getApplicationNumber())
                   .createdAt(application.getCreatedAt())
                   .userName(application.getUserName())
                   .farmName(application.getFarmName())
                   .productCategory(application.getProductCategory())
                   .status(application.getStatus())
                   .build();
    }
}
