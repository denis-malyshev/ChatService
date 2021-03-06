package com.teamdev.business.impl;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.teamdev.business.AuthenticationService;
import com.teamdev.business.impl.dto.Token;
import com.teamdev.business.impl.dto.UserEmail;
import com.teamdev.business.impl.dto.UserId;
import com.teamdev.business.impl.dto.UserPassword;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.persistence.AuthenticationTokenRepository;
import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationTokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;

    private HashFunction hashFunction = Hashing.md5();

    public AuthenticationServiceImpl() {
    }

    public Token login(UserEmail userEmail, UserPassword password) throws AuthenticationException {

        User user = userRepository.findByMail(userEmail.email);

        String passwordHash = hashFunction.newHasher().putString(password.password, Charset.defaultCharset()).hash().toString();

        if (user == null || !user.getPassword().equals(passwordHash)) {
            throw new AuthenticationException("Invalid login or password.");
        }

        AuthenticationToken token = generateToken(user.getId());
        user.setToken(token.getKey());
        return new Token(token.getKey());
    }

    @Override
    public void validation(Token token, UserId userId) throws AuthenticationException {

        AuthenticationToken innerToken = tokenRepository.findByKey(token.key);

        if (innerToken == null || innerToken.getUserId() != userId.id) {
            throw new AuthenticationException("Invalid token.");
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
