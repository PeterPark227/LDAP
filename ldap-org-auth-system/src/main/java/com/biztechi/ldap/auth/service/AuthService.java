package com.biztechi.ldap.auth.service;

import com.biztechi.ldap.auth.dto.AuthRequest;
import com.biztechi.ldap.auth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public TokenResponse login(AuthRequest authRequest) {
        String safeUsername = escapeLDAPSearchFilter(authRequest.getUsername());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        safeUsername,
                        authRequest.getPassword()
                )
        );

        String token = jwtService.generateToken(safeUsername);
        long expiresAt = jwtService.getExpirationEpoch();

        return new TokenResponse(token, expiresAt);
    }

    private String escapeLDAPSearchFilter(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            switch (c) {
                case '\\':
                    sb.append("\\5c");
                    break;
                case '*':
                    sb.append("\\2a");
                    break;
                case '(':
                    sb.append("\\28");
                    break;
                case ')':
                    sb.append("\\29");
                    break;
                case '\0':
                    sb.append("\\00");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }
}
