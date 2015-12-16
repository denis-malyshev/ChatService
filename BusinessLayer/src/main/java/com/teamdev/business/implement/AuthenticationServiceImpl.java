package com.teamdev.business.implement;

import com.teamdev.business.AuthenticationService;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.repository.AuthenticationTokenRepositoryImpl;

import java.time.LocalDateTime;

public class AuthenticationServiceImpl implements AuthenticationService<AuthenticationToken> {

    private AuthenticationTokenRepositoryImpl repository = new AuthenticationTokenRepositoryImpl();
    private long count = 0;

    public AuthenticationServiceImpl() {
    }

    public AuthenticationToken generateToken(long userId) {
        AuthenticationToken token = new AuthenticationToken(userId);
        token.setId(count++);
        repository.update(token);
        return token;
    }

    public boolean isValid(AuthenticationToken token) {
        if (token.getExpirationTime().compareTo(LocalDateTime.now()) > -1) {
            return true;
        }
        return false;
    }
}
