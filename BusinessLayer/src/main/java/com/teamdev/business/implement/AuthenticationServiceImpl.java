package com.teamdev.business.implement;

import com.teamdev.business.AuthenticationService;
import com.teamdev.business.implement.error.AuthenticationError;
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
    private long count = 0;

    public AuthenticationServiceImpl() {
    }

    public AuthenticationServiceImpl(AuthenticationTokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    public void login(String userMail, String password) throws AuthenticationError {
        User user = userRepository.findByMail(userMail);
        if (user == null) {
            throw new AuthenticationError("Invalid login or password.");
        }
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationError("Invalid login or password.");
        }
        AuthenticationToken token = generateToken(user.getId());
        user.setToken(token.getKey());
    }

    public boolean isValid(String token) throws AuthenticationError {
        AuthenticationToken innerToken = tokenRepository.findByKey(token);
        if (innerToken == null) {
            throw new AuthenticationError("Invalid token key.");
        }
        if (innerToken.getExpirationTime().compareTo(LocalDateTime.now()) < 1) {
            throw new AuthenticationError("Token has been expired.");
        }
        return true;
    }

    public void setTokenRepository(AuthenticationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private AuthenticationToken generateToken(long userId) {
        AuthenticationToken token = new AuthenticationToken(userId);
        token.setId(count++);
        tokenRepository.update(token);
        return token;
    }
}
