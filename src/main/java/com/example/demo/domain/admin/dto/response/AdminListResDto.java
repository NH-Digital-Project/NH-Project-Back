package com.example.demo.domain.admin.dto.response;

import com.example.demo.domain.admin.entity.Admin;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AdminListResDto {
    private final Integer totalCount;
    private final List<AdminSummaryDto> admins;


    public static AdminListResDto from(List<Admin> admins) {
        List<AdminSummaryDto> adminDtos = admins.stream()
            .map(AdminSummaryDto::from)
            .toList();

        return AdminListResDto.builder()
                   .totalCount(adminDtos.size())
                   .admins(adminDtos)
                   .build();
    }
}
