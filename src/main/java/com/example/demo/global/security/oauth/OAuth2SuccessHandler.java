package com.example.demo.global.security.oauth;

import com.example.demo.domain.auth.entity.RefreshToken;
import com.example.demo.domain.auth.repository.RefreshTokenRepository;
import com.example.demo.domain.auth.service.AuthService;
import com.example.demo.global.security.PrincipalDetails;
import com.example.demo.global.security.jwt.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.frontend-url}")
    private String frontendBaseUrl;

    private static final String REDIRECT_PATH = "/oauth2/redirect";
    private static final String TOKEN = "token";
    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
    private static final int REFRESH_TOKEN_MAX_AGE = 7 * 24 * 60 * 60; // 7일
    private static final String COOKIE_PATH = "/";
    private static final String SAME_SITE_POLICY = "Strict";

    private final JwtProvider jwtProvider;
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        // 로그인 성공 유저 가져옴
        PrincipalDetails oAuth2User = (PrincipalDetails) authentication.getPrincipal();

        Long userId = oAuth2User.getUserId();
        String role = oAuth2User.getRole();

        // JWT 토큰 생성
        String accessToken = jwtProvider.createAccessToken(String.valueOf(userId), role);

        String newRefreshToken = jwtProvider.createRefreshToken();
        LocalDateTime newExpiryDate = LocalDateTime.now().plusDays(7);

        authService.issueRefreshToken(userId, role, newRefreshToken, newExpiryDate);

        addRefreshTokenCookie(response, newRefreshToken);

        String targetUrl = UriComponentsBuilder.fromUriString(frontendBaseUrl + REDIRECT_PATH)
            .queryParam(TOKEN, accessToken)
            .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private static void addRefreshTokenCookie(HttpServletResponse response, String newRefreshToken) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, newRefreshToken)
            .httpOnly(true)
            .secure(true) // 로컬에서는 false 설정
            .path(COOKIE_PATH)
            .maxAge(REFRESH_TOKEN_MAX_AGE)
            .sameSite(SAME_SITE_POLICY)
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
