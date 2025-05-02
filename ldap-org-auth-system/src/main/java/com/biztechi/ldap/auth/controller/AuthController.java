package com.biztechi.ldap.auth.controller;

import com.biztechi.ldap.auth.dto.AuthRequest;
import com.biztechi.ldap.auth.dto.TokenResponse;
import com.biztechi.ldap.auth.service.AuthService;
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

    public AuthController(AuthService authService) {
        this.authService = authService;
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
}