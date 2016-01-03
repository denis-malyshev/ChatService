package com.teamdev.business.impl;

import com.teamdev.business.AuthenticationService;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.tinytypes.Token;
import com.teamdev.business.tinytypes.UserEmail;
import com.teamdev.business.tinytypes.UserPassword;
import com.teamdev.persistence.AuthenticationTokenRepository;
import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationTokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;

    public AuthenticationServiceImpl() {
    }

    public Token login(UserEmail userEmail, UserPassword password) throws AuthenticationException {
        User user = userRepository.findByMail(userEmail.getEmail());

        if (user == null || !user.getPassword().equals(password.getPassword())) {
            throw new AuthenticationException("Invalid login or password.");
        }

        AuthenticationToken token = generateToken(user.getId());
        user.setToken(token.getKey());
        return new Token(token.getKey());
    }

    private AuthenticationToken generateToken(long userId) {
        AuthenticationToken token = new AuthenticationToken(userId);
        tokenRepository.update(token);
        return token;
    }
}
