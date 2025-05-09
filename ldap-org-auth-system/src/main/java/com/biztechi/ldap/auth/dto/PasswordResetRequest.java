package com.biztechi.ldap.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "비밀번호 초기화 정보")
public class PasswordResetRequest {

    @Schema(description = "사용자 ID", example = "admin", required = true)
    private String username;

    @Schema(description = "변경 비밀번호", example = "1234qwer", required = true)
    private String newPassword;
}
