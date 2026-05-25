package com.example.demo.global.security;

import com.example.demo.global.security.jwt.JwtAuthenticationFilter;
import com.example.demo.global.security.oauth.CustomOAuth2UserService;
import com.example.demo.global.security.oauth.OAuth2SuccessHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${app.frontend-url}")
    private String frontendUrl;

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
        JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(
                    (request, response, authException) ->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                )
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/oauth2/**", "/login/oauth2/code/**", "/api/v1/projects/**").permitAll()
                .requestMatchers("/api/v1/admin/login").permitAll()
                .requestMatchers("/api/v1/applications/**", "/api/v1/me/**").authenticated()
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)
                )
                .successHandler(oAuth2SuccessHandler)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 허용할 출처(Origin) 설정
        configuration.setAllowedOrigins(List.of(frontendUrl));

        // 허용할 HTTP 메서드 설정
        configuration.setAllowedMethods(
            List.of(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name()));

        // 허용할 헤더 설정
        configuration.setAllowedHeaders(
            List.of(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE,
                HttpHeaders.CACHE_CONTROL));

        // 자격증명(쿠키, 세션, 인증헤더 등) 포함 여부 결정
        configuration.setAllowCredentials(true);

        // 브라우저가 응답에서 접근할 수 있도록 노출할 헤더 설정
        configuration.setExposedHeaders(List.of(HttpHeaders.AUTHORIZATION));

        // 모든 URL 경로(/**)에 대해 위 설정 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
