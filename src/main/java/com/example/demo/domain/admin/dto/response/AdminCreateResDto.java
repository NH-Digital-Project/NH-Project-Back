package com.example.demo.domain.admin.dto.response;

import com.example.demo.domain.admin.entity.Admin;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AdminCreateResDto {
    private Long adminId;
    private String adminLoginId;
    private LocalDateTime createdAt;

    public static AdminCreateResDto from(Admin admin){
        return AdminCreateResDto.builder()
                   .adminId(admin.getId())
                   .adminLoginId(admin.getLoginId())
                   .createdAt(admin.getCreatedAt())
                   .build();
    }
}
