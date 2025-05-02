package com.biztechi.ldap.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * application.yml에서 ldap 접두사의 설정 정보를 바인딩하는 클래스
 */
@Component
@ConfigurationProperties(prefix = "ldap")
@Getter
@Setter
@Schema(description = "LDAP 서버 설정 정보 (application.yml에서 매핑됨)")
public class LdapProperties {

    @Schema(description = "LDAP 서버 URL (예: ldap://localhost:389)")
    private String url;

    @Schema(description = "LDAP Base DN (예: dc=example,dc=org)")
    private String base;

    @Schema(description = "LDAP 인증용 관리자 DN")
    private String userDn;

    @Schema(description = "LDAP 인증용 관리자 비밀번호")
    private String password;
}
