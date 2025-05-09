package com.biztechi.ldap.auth.controller;

import com.biztechi.ldap.auth.dto.AuthRequest;
import com.biztechi.ldap.auth.dto.PasswordChangeRequest;
import com.biztechi.ldap.auth.dto.PasswordResetRequest;
import com.biztechi.ldap.auth.dto.TokenResponse;
import com.biztechi.ldap.auth.service.AuthService;
import com.biztechi.ldap.auth.service.LdapUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * LDAP 로그인 및 JWT 발급 컨트롤러
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final LdapUserService ldapUserService;

    public AuthController(AuthService authService, LdapUserService ldapUserService) {
        this.authService = authService;
        this.ldapUserService = ldapUserService;
    }

    @Operation(summary = "LDAP 로그인", description = "LDAP 인증을 통해 JWT 토큰을 발급합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "JWT 발급 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패 (ID 또는 비밀번호 오류)")
    })

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            TokenResponse tokenResponse = authService.login(request);
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
    }

    @Operation(summary = "LDAP 비밀번호 변경", description = "현재 비밀번호를 기반으로 새 비밀번호로 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
            @ApiResponse(responseCode = "400", description = "비밀번호 변경 실패")
    })
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordChangeRequest request) {
        try {
            ldapUserService.changePasswordIfOldValid(request.getUsername(), request.getCurrentPassword(), request.getNewPassword());
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 변경 실패: " + e.getMessage());
        }
    }

    @Operation(summary = "LDAP 비밀번호 초기화", description = "관리자가 사용자의 비밀번호를 초기화합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 초기화 성공"),
            @ApiResponse(responseCode = "400", description = "비밀번호 초기화 실패")
    })
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid PasswordResetRequest request) {
        try {
            ldapUserService.resetPassword(request.getUsername(), request.getNewPassword());
            return ResponseEntity.ok("비밀번호가 초기화되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 초기화 실패: " + e.getMessage());
        }
    }

    @Operation(summary = "로그아웃", description = "클라이언트에서 JWT 제거를 통해 로그아웃 처리.")
    @ApiResponse(responseCode = "200", description = "로그아웃 완료")
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // JWT는 상태가 없으므로 클라이언트에서 삭제하면 됨
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
