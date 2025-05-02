package com.biztechi.ldap.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * JWT 발급 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "JWT 발급 응답 정보")
public class TokenResponse {

    @Schema(description = "JWT Access Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI...")
    private String accessToken;

    @Schema(description = "Access Token 만료 시각 (Epoch milliseconds)", example = "1713500000000")
    private long expiresAt;
}
