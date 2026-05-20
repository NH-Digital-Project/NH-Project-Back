package com.example.demo.global.security.oauth;

import com.example.demo.global.security.PrincipalDetails;
import com.example.demo.global.security.jwt.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        // 로그인 성공 유저 가져옴
        PrincipalDetails oAuth2User = (PrincipalDetails) authentication.getPrincipal();

        Long userId = oAuth2User.getUserId();
        String role = oAuth2User.getRole();

        // JWT 토큰 생성
        String accessToken = jwtProvider.createAccessToken(String.valueOf(userId), role);

        String targetUrl = UriComponentsBuilder.fromUriString(frontendBaseUrl + REDIRECT_PATH)
            .queryParam(TOKEN, accessToken)
            .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
