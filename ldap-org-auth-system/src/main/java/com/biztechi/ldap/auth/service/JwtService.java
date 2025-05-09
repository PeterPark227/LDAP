package com.biztechi.ldap.auth.service;

import com.biztechi.ldap.config.JwtProperties;
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

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * 사용자 이름을 기반으로 JWT Access Token 생성
     */
    public String generateToken(String username) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.getExpirationMs());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .compact();
    }

    /**
     * 현재 기준 Access Token 만료 시각 반환
     */
    public long getExpirationEpoch() {
        return new Date().getTime() + jwtProperties.getExpirationMs();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
