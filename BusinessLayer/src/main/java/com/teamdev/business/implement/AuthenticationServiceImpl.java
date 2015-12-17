package com.teamdev.business.implement;

import com.teamdev.business.AuthenticationService;
import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.exception.EntityNotFoundException;
import com.teamdev.persistence.repository.AuthenticationTokenRepository;
import com.teamdev.persistence.repository.UserRepository;

import java.time.LocalDateTime;

public class AuthenticationServiceImpl implements AuthenticationService<AuthenticationToken> {

    private AuthenticationTokenRepository tokenRepository;
    private UserRepository userRepository;
    private long count = 0;

    public AuthenticationServiceImpl(AuthenticationTokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    public void login(String userMail, String password) throws EntityNotFoundException {
        User user = userRepository.findByMail(userMail);
        AuthenticationToken token = generateToken(user.getId());
        user.setToken(token);
    }

    public boolean isValid(AuthenticationToken token) throws AuthenticationError, EntityNotFoundException {
        AuthenticationToken innerToken = tokenRepository.findById(token.getId());
        if (!innerToken.getKey().equals(token.getKey())) {
            throw new AuthenticationError("Invalid token key.");
        }
        if (token.getExpirationTime().compareTo(LocalDateTime.now()) < 1) {
            throw new AuthenticationError("Token has been expired.");
        }
        return true;
    }

    public AuthenticationTokenRepository getTokenRepository() {
        return tokenRepository;
    }

    private AuthenticationToken generateToken(long userId) {
        AuthenticationToken token = new AuthenticationToken(userId);
        token.setId(count++);
        tokenRepository.update(token);
        return token;
    }
}
