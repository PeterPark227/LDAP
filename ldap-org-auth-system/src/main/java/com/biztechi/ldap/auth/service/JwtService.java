package com.biztechi.ldap.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

/**
 * JWT 생성/검증 관련 서비스
 */
@Service
public class JwtService {

    // 실제 운영 시에는 환경 변수나 Secret Manager를 통해 관리해야 함
    private final String secretKey = "my-secret-key";

    // Access Token 유효시간: 1시간 (ms)
    private final long validityInMilliseconds = 60 * 60 * 1000;

    /**
     * 사용자 이름을 기반으로 JWT Access Token 생성
     */
    public String generateToken(String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(validityInMilliseconds)))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * 현재 기준 Access Token 만료 시각 반환
     */
    public long getExpirationEpoch() {
        return Instant.now().toEpochMilli() + validityInMilliseconds;
    }

    public boolean validate(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // 토큰의 subject 필드가 username 역할
    }

}
