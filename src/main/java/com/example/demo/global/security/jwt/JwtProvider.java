package com.example.demo.global.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private static final String ROLE = "role";

    private final SecretKey key;
    private final long accessTokenValidityTime;

    public JwtProvider(
        @Value("${jwt.secret}") String secretKey,
        @Value("${jwt.access-expiration}") long accessTokenValidityTime) {

        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);

        this.accessTokenValidityTime = accessTokenValidityTime;
    }

    public String createAccessToken(String naverId, String role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidityTime);

        return Jwts.builder()
            .subject(naverId)
            .claim(ROLE, role)
            .issuedAt(now)
            .expiration(validity)
            .signWith(key)
            .compact();
    }
}
