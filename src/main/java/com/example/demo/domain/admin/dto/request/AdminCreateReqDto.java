package com.example.demo.domain.admin.dto.request;


import com.example.demo.domain.admin.entity.Admin;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AdminCreateReqDto {
    private String adminLoginId;
    private String password;

    public Admin toEntity(){
        return Admin.builder()
                   .loginId(adminLoginId)
                   .password(password)
                   .build();
    }
}
