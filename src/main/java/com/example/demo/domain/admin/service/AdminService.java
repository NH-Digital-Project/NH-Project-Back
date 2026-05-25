package com.example.demo.domain.admin.service;

import com.example.demo.domain.admin.dto.request.AdminCreateReqDto;
import com.example.demo.domain.admin.dto.request.AdminLoginReqDto;
import com.example.demo.domain.admin.dto.response.AdminCreateResDto;
import com.example.demo.domain.admin.dto.response.AdminListResDto;
import com.example.demo.domain.admin.dto.response.AdminLoginResDto;
import com.example.demo.domain.admin.dto.response.ApplicationListResDto;
import com.example.demo.domain.admin.dto.response.UserListResDto;
import com.example.demo.domain.admin.entity.Admin;
import com.example.demo.domain.admin.repository.AdminRepository;
import com.example.demo.domain.application.entity.Application;
import com.example.demo.domain.application.repository.ApplicationRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import com.example.demo.global.security.jwt.JwtProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;
    private final ApplicationRepository applicationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;


    @Transactional
    public AdminCreateResDto createAdmin(AdminCreateReqDto createReqDto) {
        validateDuplicateLoginId(createReqDto.getAdminLoginId());

        Admin admin = adminRepository.save(createReqDto.toEntity(passwordEncoder.encode(
            createReqDto.getPassword())));

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

        List<Admin> admins = adminRepository.findAll();

        return AdminListResDto.from(admins);
    }

    public AdminLoginResDto adminLogin(AdminLoginReqDto reqDto) {
        Admin admin = adminRepository.findByLoginId(reqDto.adminLoginId())
            .orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));

        if (!passwordEncoder.matches(reqDto.password(), admin.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtProvider.createAccessToken(
            String.valueOf(admin.getId()), admin.getRole().name()
        );

        return new AdminLoginResDto(accessToken);
    }


    private void validateDuplicateLoginId(String targetAdminLoginId) {
        if (adminRepository.existsByLoginId(targetAdminLoginId)) {
            throw new CustomException(ErrorCode.DUPLICATED_LOGIN_ID);
        }
    }

    private Admin findAdminById(Long targetAdminId) {
        return adminRepository.findById(targetAdminId).orElseThrow(
            () -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)
        );
    }

    private void validateAdminId(Long requestAdminId) {
        adminRepository.findById(requestAdminId).orElseThrow(
            () -> new CustomException(ErrorCode.ADMIN_UNAUTHORIZED)
        );
    }


    public ApplicationListResDto getApplications(Long userId , Pageable pageable, String keyword) {

        validateAdminId(userId);


        Page<Application> applicationPage = findApplications(keyword, pageable);

        if (applicationPage.getTotalElements() == 0) {
            return ApplicationListResDto.from(applicationPage);
        }

        if (pageable.getPageNumber() >= applicationPage.getTotalPages()) {
            pageable = PageRequest.of(applicationPage.getTotalPages() - 1, pageable.getPageSize(),
                pageable.getSort());
            applicationPage = findApplications(keyword, pageable);
        }




        return ApplicationListResDto.from(applicationPage);
    }
    private Page<Application> findApplications(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return applicationRepository.findAll(pageable);
        }
        return applicationRepository.findByUserNameContainingOrFarmNameContaining(keyword, keyword, pageable);
    }

    public UserListResDto getUsers(Long userId, Pageable pageable, String keyword) {
        validateAdminId(userId);

        Page<User> userPage = findUsers(keyword, pageable);

        if (userPage.getTotalElements() == 0) {
            return UserListResDto.from(userPage);
        }

        if (pageable.getPageNumber() >= userPage.getTotalPages()) {
            pageable = PageRequest.of(userPage.getTotalPages() - 1, pageable.getPageSize(),
                pageable.getSort());
            userPage = findUsers(keyword, pageable);
        }

        return UserListResDto.from(userPage);
    }
    private Page<User> findUsers(String keyword , Pageable pageable){
        if(keyword == null || keyword.isBlank()){
            return userRepository.findAll(pageable);
        }

        return userRepository.findByUserNameContainingOrEmailContaining(keyword, keyword, pageable);
    }
}
