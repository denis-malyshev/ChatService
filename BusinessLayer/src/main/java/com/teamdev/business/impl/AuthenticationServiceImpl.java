package com.teamdev.business.impl;

import com.teamdev.business.AuthenticationService;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.persistence.AuthenticationTokenRepository;
import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationTokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;

    public AuthenticationServiceImpl() {
    }

    public String login(String userMail, String password) throws AuthenticationException {
        User user = userRepository.findByMail(userMail);
        if (user == null) {
            throw new AuthenticationException("Invalid login or password.");
        }
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("Invalid login or password.");
        }
        AuthenticationToken token = generateToken(user.getId());
        user.setToken(token.getKey());
        return token.getKey();
    }

    public void validation(String token) throws AuthenticationException {
        AuthenticationToken innerToken = tokenRepository.findByKey(token);
        if (innerToken == null) {
            throw new AuthenticationException("Invalid token key.");
        }
        if (innerToken.getExpirationTime().compareTo(LocalDateTime.now()) < 1) {
            throw new AuthenticationException("Token has been expired.");
        }
    }

    private AuthenticationToken generateToken(long userId) {
        AuthenticationToken token = new AuthenticationToken(userId);
        tokenRepository.update(token);
        return token;
    }
}
