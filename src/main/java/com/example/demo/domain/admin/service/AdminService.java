package com.example.demo.domain.admin.service;

import com.example.demo.domain.admin.dto.request.AdminCreateReqDto;
import com.example.demo.domain.admin.dto.response.AdminCreateResDto;
import com.example.demo.domain.admin.entity.Admin;
import com.example.demo.domain.admin.repository.AdminRepository;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;

    @Transactional
    public AdminCreateResDto createAdmin(AdminCreateReqDto createReqDto) {
        validateDuplicateLoginId(createReqDto.getAdminLoginId());

        // Todo 우선 평문 저장 Security 도입 후 비밀번호 암호화 저장

        Admin admin = adminRepository.save(createReqDto.toEntity());

        return AdminCreateResDto.from(admin);


    }

    @Transactional
    public void deleteAdmin(Long adminId) {
        Admin admin = findAdminById(adminId);

        adminRepository.delete(admin);
    }

    private void validateDuplicateLoginId(String adminLoginId){
        if(adminRepository.existsByLoginId(adminLoginId)){
            throw new CustomException(ErrorCode.DUPLICATED_LOGIN_ID);
        }
    }

    private Admin findAdminById(Long adminId){
        return adminRepository.findById(adminId).orElseThrow(
            () -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)
        );
    }


}
