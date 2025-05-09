package com.biztechi.ldap.auth.service;

import com.biztechi.ldap.auth.util.LdapShaEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LdapUserService {

    private final LdapTemplate ldapTemplate;

    @Value("${spring.ldap.base}")
    private String baseDn;

    /**
     * 사용자 비밀번호 변경
     * @param uid
     * @param newPassword
     */
    public void changePassword(String uid, String newPassword) {
        String dn = buildUserDn(uid);
        String passwordHash = LdapShaEncoder.shaBase64(newPassword);

        ModificationItem[] mods = new ModificationItem[]{
                new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                        new BasicAttribute("userPassword", passwordHash))
        };

        ldapTemplate.modifyAttributes(dn, mods);
    }

    /**
     * 사용자 비밀번호 변경
     *
     * @param uid
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public void changePasswordIfOldValid(String uid, String oldPassword, String newPassword) {

        String dn = buildUserDn(uid);

        // 사용자 현재 정보 가져오기
        DirContextOperations user = ldapTemplate.lookupContext(dn);
        String currentPasswordHash = (String) user.getStringAttribute("userPassword");

        String oldPasswordHash = LdapShaEncoder.shaBase64(oldPassword);

        if (!oldPasswordHash.equals(currentPasswordHash)) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }


        // 새 비밀번호로 변경
        String newPasswordHash = LdapShaEncoder.shaBase64(newPassword);
        ModificationItem[] mods = new ModificationItem[]{
                new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                        new BasicAttribute("userPassword", newPasswordHash))
        };
        ldapTemplate.modifyAttributes(dn, mods);
    }

    /**
     * 사용자 비밀번호 초기화 (관리자용)
     * @param uid
     * @param newPassword
     */
    public void resetPassword(String uid, String newPassword) {
        changePassword(uid, newPassword);
    }

    /**
     * 사용자 존재 여부 확인
     * @param uid
     * @return
     */
    public boolean exists(String uid) {
        LdapQuery query = LdapQueryBuilder.query()
                .base("ou=A," + baseDn)
                .where("uid").is(uid);
        List<?> result = ldapTemplate.search(query, (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());
        return !result.isEmpty();
    }

    /**
     * UserDN 생성
     * @param uid
     * @return
     */
    private String buildUserDn(String uid) {
        return "uid=" + uid + ",ou=A," + baseDn;
    }
}