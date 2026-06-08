package com.example.demo.domain.auth.repository;

import com.example.demo.domain.auth.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByPrincipalIdAndRole(Long id, String role);
}
