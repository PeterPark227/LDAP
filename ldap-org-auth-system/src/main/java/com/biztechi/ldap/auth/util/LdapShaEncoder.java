package com.biztechi.ldap.auth.util;

import java.security.MessageDigest;
import java.util.Base64;

public class LdapShaEncoder {

    public static String shaBase64(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(password.getBytes("UTF-8"));
            return "{SHA}" + Base64.getEncoder().encodeToString(digest);
        } catch (Exception e) {
            throw new RuntimeException("SHA-1 암호화 실패", e);
        }
    }
}