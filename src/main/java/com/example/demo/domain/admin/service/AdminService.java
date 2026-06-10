package com.example.demo.domain.admin.service;

import com.example.demo.domain.admin.dto.request.AdminCreateReqDto;
import com.example.demo.domain.admin.dto.response.AdminCreateResDto;
import com.example.demo.domain.admin.dto.response.AdminListResDto;
import com.example.demo.domain.admin.dto.response.ApplicationListResDto;
import com.example.demo.domain.admin.dto.response.UserListResDto;
import com.example.demo.domain.admin.dto.response.UserSummaryDto;
import com.example.demo.domain.admin.entity.Admin;
import com.example.demo.domain.admin.repository.AdminRepository;
import com.example.demo.domain.application.dto.response.ApplicationOptionDto;
import com.example.demo.domain.application.dto.response.ApplicationResDto;
import com.example.demo.domain.application.entity.Application;
import com.example.demo.domain.application.repository.ApplicationRepository;
import com.example.demo.domain.application.service.ApplicationService;
import com.example.demo.domain.application.status.ApplicationStatus;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final UserRepository userRepository;
    private final ApplicationService applicationService;


    @Transactional
    public AdminCreateResDto createAdmin(Long adminId ,AdminCreateReqDto createReqDto) {
        validateAdminId(adminId);

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


    public ApplicationListResDto getApplications(Long userId , Pageable pageable, String keyword, List<ApplicationStatus> status) {

        validateAdminId(userId);

        Page<Application> applicationPage = findApplications(keyword, status, pageable);

        if (applicationPage.getTotalElements() == 0) {
            return ApplicationListResDto.from(applicationPage);
        }

        if (pageable.getPageNumber() >= applicationPage.getTotalPages()) {
            pageable = PageRequest.of(applicationPage.getTotalPages() - 1, pageable.getPageSize(),
                pageable.getSort());
            applicationPage = findApplications(keyword, status, pageable);
        }

        return ApplicationListResDto.from(applicationPage);
    }
    private Page<Application> findApplications(String keyword, List<ApplicationStatus> status, Pageable pageable) {
        boolean hasKeyword = (keyword != null && !keyword.isBlank());
        boolean hasStatuses = (status != null && !status.isEmpty());

        if(hasKeyword && hasStatuses) {
            return applicationRepository.findByKeywordAndStatuses(keyword, status, pageable);
        } else if(hasKeyword) {
            return applicationRepository.findByUserNameContainingOrBusinessNameContaining(keyword, keyword, pageable);
        } else if(hasStatuses) {
            return applicationRepository.findByStatusIn(status, pageable);
        } else {
            return applicationRepository.findAll(pageable);
        }
    }

    public UserListResDto getUsers(Long userId, Pageable pageable, String keyword) {
        validateAdminId(userId);

        Page<User> userPage = findUsers(keyword, pageable);

        if (userPage.getTotalElements() == 0) {
            return UserListResDto.of(userPage, List.of());
        }

        if (pageable.getPageNumber() >= userPage.getTotalPages()) {
            pageable = PageRequest.of(userPage.getTotalPages() - 1, pageable.getPageSize(),
                pageable.getSort());
            userPage = findUsers(keyword, pageable);
        }

        // 조회된 유저들의 id만 추출
        List<Long> userIds = userPage.getContent().stream()
                .map(User::getId)
                .toList();

        // 해당 유저들 중 지원서가 있는 유저 id만 조회
        List<Long> appliedUserIds = applicationRepository.findUserIdsByUserIdInAndStatusNot(userIds, ApplicationStatus.CANCELED);

        // 조회한 id 목록에 포함되어 있는지 확인하여 true/false 매핑
        List<UserSummaryDto> summaryDtos = userPage.getContent().stream()
                .map(user -> UserSummaryDto.from(user, appliedUserIds.contains(user.getId())))
                .toList();

        return UserListResDto.of(userPage, summaryDtos);
    }

    public ApplicationResDto getApplication(Long adminId, Long applicationId) {
        validateAdminId(adminId);
        return applicationService.getApplication(applicationId);
    }

    public List<ApplicationOptionDto> getSubmittedApplicationOptions(Long adminId) {
        validateAdminId(adminId);
        return applicationService.getSubmittedApplicationOptions();
    }

    public Admin getAdminByLoginId(String loginId) {
        return adminRepository.findByLoginId(loginId)
            .orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));
    }

    private Page<User> findUsers(String keyword , Pageable pageable){
        if(keyword == null || keyword.isBlank()){
            return userRepository.findAll(pageable);
        }

        return userRepository.findByUserNameContaining(keyword, keyword, pageable);
    }
}
