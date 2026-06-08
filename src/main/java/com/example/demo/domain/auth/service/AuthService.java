package com.example.demo.domain.auth.service;

import com.example.demo.domain.auth.dto.request.AdminLoginReqDto;
import com.example.demo.domain.auth.dto.response.AdminLoginResDto;
import com.example.demo.domain.admin.entity.Admin;
import com.example.demo.domain.admin.service.AdminService;
import com.example.demo.domain.auth.entity.RefreshToken;
import com.example.demo.domain.auth.repository.RefreshTokenRepository;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import com.example.demo.global.security.jwt.JwtProvider;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshRepository;

    @Transactional
    public AdminLoginResDto adminLogin(AdminLoginReqDto reqDto) {
        Admin admin = adminService.getAdminByLoginId(reqDto.adminLoginId());

        if (!passwordEncoder.matches(reqDto.password(), admin.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtProvider.createAccessToken(String.valueOf(admin.getId()),
            admin.getRole().name());

        String newRefreshToken = jwtProvider.createRefreshToken();
        LocalDateTime newExpiryDate = LocalDateTime.now().plusDays(7);

        refreshRepository.findByPrincipalIdAndRole(admin.getId(), admin.getRole().name())
            .ifPresentOrElse(
                token -> token.updateToken(newRefreshToken, newExpiryDate),
                () -> refreshRepository.save(
                    RefreshToken.from(admin.getId(), admin.getRole().name(), newRefreshToken,
                        newExpiryDate))
            );

        return new AdminLoginResDto(accessToken, newRefreshToken);
    }
}
