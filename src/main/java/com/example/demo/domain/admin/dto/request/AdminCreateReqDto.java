package com.example.demo.domain.admin.dto.request;


import com.example.demo.domain.admin.entity.Admin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AdminCreateReqDto {
    private final String adminLoginId;
    private final String password;

    public Admin toEntity(){
        return Admin.builder()
                   .loginId(adminLoginId)
                   .password(password)
                   .build();
    }
}
