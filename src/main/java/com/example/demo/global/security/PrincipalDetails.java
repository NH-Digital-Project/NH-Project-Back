package com.example.demo.global.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class PrincipalDetails implements UserDetails, OAuth2User {

    private final Long userId;
    private final String role;
    private Map<String, Object> attributes;

    // JWT 필터용
    public PrincipalDetails(Long userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    // 네이버 로그인 직후
    public PrincipalDetails(Long userId, String role, Map<String, Object> attributes) {
        this.userId = userId;
        this.role = role;
        this.attributes = attributes;
    }

    public Long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getName() {
        return String.valueOf(userId);
    }

    @Override
    public String getUsername() {
        return String.valueOf(userId);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }
}
