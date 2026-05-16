package com.example.demo.domain.admin.dto.response;

import com.example.demo.domain.admin.entity.Admin;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Builder
@Getter
public class AdminCreateResDto {
    private final Long adminId;
    private final String adminLoginId;
    private final LocalDateTime createdAt;

    public static AdminCreateResDto from(Admin admin){
        return AdminCreateResDto.builder()
                   .adminId(admin.getId())
                   .adminLoginId(admin.getLoginId())
                   .createdAt(admin.getCreatedAt())
                   .build();
    }
}
