package com.example.demo.domain.admin.dto.response;

import com.example.demo.domain.application.entity.Application;
import com.example.demo.domain.application.status.ApplicationStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApplicationSummaryDto {

    private final Long applicationId;
    private final String applicationNumber;
    private final LocalDateTime createdAt;
    private final String userName;
    private final String businessName;
    private final String productCategory;
    private final ApplicationStatus status;
    private final LocalDateTime deletedAt;

    public static ApplicationSummaryDto from(Application application){
        return ApplicationSummaryDto.builder()
                   .applicationId(application.getId())
                   .applicationNumber(application.getApplicationNumber())
                   .createdAt(application.getCreatedAt())
                   .userName(application.getUserName())
                   .businessName(application.getBusinessName())
                   .productCategory(application.getProductCategory())
                   .status(application.getStatus())
                .deletedAt(application.getDeletedAt())
                   .build();
    }
}
