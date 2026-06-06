package com.example.demo.domain.application.dto.response;

import com.example.demo.domain.application.entity.Application;

public record ApplicationOptionDto(
    Long applicationId,
    String applicationNumber,
    String businessName,
    String productCategory
) {

    public static ApplicationOptionDto from(Application application) {
        return new ApplicationOptionDto(application.getId(), application.getApplicationNumber(),
            application.getBusinessName(), application.getProductCategory());
    }
}
