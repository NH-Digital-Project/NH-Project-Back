package com.example.demo.domain.admin.service;

import com.example.demo.domain.admin.dto.request.AdminCreateReqDto;
import com.example.demo.domain.admin.dto.response.AdminCreateResDto;
import com.example.demo.domain.admin.dto.response.AdminListResDto;
import com.example.demo.domain.admin.entity.Admin;
import com.example.demo.domain.admin.repository.AdminRepository;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import java.util.List;
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
    public void deleteAdmin(Long requestAdminId, Long targetAdminId) {

        validateAdminId(requestAdminId);

        Admin targetAdmin = findAdminById(targetAdminId);

        adminRepository.delete(targetAdmin);
    }

    public AdminListResDto getAdmins(Long requestAdminId) {

        validateAdminId(requestAdminId);

        List<Admin> admins  = adminRepository.findAll();

        return AdminListResDto.from(admins);
    }

    private void validateDuplicateLoginId(String targetAdminLoginId){
        if(adminRepository.existsByLoginId(targetAdminLoginId)){
            throw new CustomException(ErrorCode.DUPLICATED_LOGIN_ID);
        }
    }

    private Admin findAdminById(Long targetAdminId){
        return adminRepository.findById(targetAdminId).orElseThrow(
            () -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)
        );
    }

    private void validateAdminId(Long targetAdminId) {
      adminRepository.findById(targetAdminId).orElseThrow(
          ()-> new CustomException(ErrorCode.ADMIN_NOT_FOUND)
      );
    }



}
