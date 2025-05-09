package com.biztechi.ldap.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * application.yml에서 jwt 접두사의 설정 정보를 바인딩하는 클래스
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
@Schema(description = "jwt 설정 정보 (application.yml에서 매핑됨)")
public class JwtProperties {

    @Schema(description = "토큰 생성 암호화 키")
    private String secretKey;

    @Schema(description = "Access Token 유효시간")
    private long expirationMs;
}
