package com.example.demo.global.security.oauth;

import com.example.demo.global.security.jwt.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    private static final String USER_NAME_ATTRIBUTE_NAME = "response";
    private static final String ID_KEY = "id";
    private static final String URI_STR = "http://localhost:3000/oauth2/redirect";
    private static final String TOKEN = "token";

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        // 로그인 성공 유저 가져옴
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        Map<String, Object> attributes = (Map<String, Object>) oAuth2User.getAttributes().get(USER_NAME_ATTRIBUTE_NAME);

        String naverId = (String) attributes.get(ID_KEY);

        String role = authentication.getAuthorities().iterator().next().getAuthority();

        // JWT 토큰 생성
        String accessToken = jwtProvider.createAccessToken(naverId, role);

        String targetUrl = UriComponentsBuilder.fromUriString(URI_STR)
            .queryParam(TOKEN, accessToken)
            .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
