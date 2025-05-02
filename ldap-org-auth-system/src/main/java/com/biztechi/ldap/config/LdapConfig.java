package com.biztechi.ldap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * LDAP 연결 및 LdapTemplate Bean 등록 설정 클래스
 */
@Configuration
public class LdapConfig {

    private final LdapProperties ldapProperties;

    public LdapConfig(LdapProperties ldapProperties) {
        this.ldapProperties = ldapProperties;
    }

    /**
     * LdapContextSource 빈 등록
     * LDAP 서버 연결을 위한 기본 정보 (url, baseDn, userDn, password) 설정
     */
    @Bean
    public LdapContextSource ldapContextSource() {
        LdapContextSource source = new LdapContextSource();
        source.setUrl(ldapProperties.getUrl());
        source.setBase(ldapProperties.getBase());
        source.setUserDn(ldapProperties.getUserDn());
        source.setPassword(ldapProperties.getPassword());
        return source;
    }

    /**
     * LdapTemplate 빈 등록
     * LDAP 서버에 질의/검색/인증 등을 수행할 수 있도록 지원하는 템플릿
     */
    @Bean
    public LdapTemplate ldapTemplate(LdapContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }
}
