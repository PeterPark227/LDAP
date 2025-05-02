package com.biztechi.ldap.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 요청 DTO
 */
@Getter
@Setter
@Schema(description = "로그인 요청 정보")
public class AuthRequest {

    @Schema(description = "사용자 ID", example = "admin", required = true)
    private String username;

    @Schema(description = "사용자 비밀번호", example = "1234", required = true)
    private String password;
}
