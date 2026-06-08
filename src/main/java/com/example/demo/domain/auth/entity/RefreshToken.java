package com.example.demo.domain.auth.entity;

import com.example.demo.global.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "refresh_tokens")
@Entity
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long principalId;

    private String role;

    private String token;

    private LocalDateTime expiryDate;

    private RefreshToken(Long principalId, String role, String token, LocalDateTime expiryDate) {
        this.principalId = principalId;
        this.role = role;
        this.token = token;
        this.expiryDate = expiryDate;
    }

    public static RefreshToken from(Long principalId, String role, String token,
        LocalDateTime expiryDate) {
        return new RefreshToken(principalId, role, token, expiryDate);
    }

    public void updateToken(String newToken, LocalDateTime newExpiryDate) {
        this.token = newToken;
        this.expiryDate = newExpiryDate;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.getExpiryDate());
    }
}
