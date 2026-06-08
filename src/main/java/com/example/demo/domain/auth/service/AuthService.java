package com.example.demo.domain.auth.service;

import com.example.demo.domain.admin.entity.Admin;
import com.example.demo.domain.admin.service.AdminService;
import com.example.demo.domain.auth.dto.request.AdminLoginReqDto;
import com.example.demo.domain.auth.dto.response.AdminLoginResDto;
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

        TokenPair tokenPair = issueTokens(admin.getId(), admin.getRole().name());

        issueRefreshToken(admin.getId(), admin.getRole().name(), tokenPair.refreshToken, tokenPair.expiryDate);

        return new AdminLoginResDto(tokenPair.accessToken, tokenPair.refreshToken);
    }

    @Transactional
    public void issueRefreshToken(Long principalId, String role, String newRefreshToken,
        LocalDateTime newExpiryDate) {
        refreshRepository.findByPrincipalIdAndRole(principalId, role)
            .ifPresentOrElse(
                token -> token.updateToken(newRefreshToken, newExpiryDate),
                () -> refreshRepository.save(
                    RefreshToken.from(principalId, role, newRefreshToken, newExpiryDate))
            );
    }


    private TokenPair issueTokens(Long userId, String role) {

        String accessToken = jwtProvider.createAccessToken(String.valueOf(userId), role);

        String refreshToken = jwtProvider.createRefreshToken();

        LocalDateTime expiryDate = LocalDateTime.now().plusDays(7);

        return new TokenPair(accessToken, refreshToken, expiryDate);
    }

    private record TokenPair(
        String accessToken,
        String refreshToken,
        LocalDateTime expiryDate
    ) {}
}
