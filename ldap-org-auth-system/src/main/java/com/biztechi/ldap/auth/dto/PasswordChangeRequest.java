package com.biztechi.ldap.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "비밀번호 변경 정보")
public class PasswordChangeRequest {
    @Schema(description = "사용자 ID", example = "admin", required = true)
    private String username;

    @Schema(description = "현재 비밀번호", example = "qwer1234", required = true)
    private String currentPassword;

    @Schema(description = "변경 비밀번호", example = "1234qwer", required = true)
    private String newPassword;
}
