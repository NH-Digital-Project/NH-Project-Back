package com.example.demo.domain.admin.dto.response;

import com.example.demo.domain.application.entity.Application;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Builder
@Getter
public class ApplicationListResDto {

    private final int currentPage;
    private final Long totalCount;
    private final int totalPages;
    private final List<ApplicationSummaryDto> applications;

    public static ApplicationListResDto from(Page<Application> applications) {
        List<ApplicationSummaryDto> applicationDtos = applications.stream()
                                                          .map(ApplicationSummaryDto::from)
                                                          .toList();
        return ApplicationListResDto.builder()
                   .currentPage(applications.getNumber() + 1)
                   .totalCount(applications.getTotalElements())
                   .totalPages(applications.getTotalPages())
                   .applications(applicationDtos)
                   .build();
    }


}
