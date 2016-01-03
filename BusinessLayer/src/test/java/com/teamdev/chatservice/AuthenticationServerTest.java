package com.teamdev.chatservice;

import com.teamdev.business.AuthenticationService;
import com.teamdev.business.impl.application.ApplicationConfig;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.tinytypes.Token;
import com.teamdev.business.tinytypes.UserEmail;
import com.teamdev.business.tinytypes.UserPassword;
import com.teamdev.persistence.AuthenticationTokenRepository;
import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.User;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class AuthenticationServerTest {

    ApplicationContext context;
    AuthenticationService tokenService;
    AuthenticationTokenRepository tokenRepository;
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {

        context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        tokenService = context.getBean(AuthenticationService.class);
        tokenRepository = context.getBean(AuthenticationTokenRepository.class);
        userRepository = context.getBean(UserRepository.class);
    }

    @Test
    public void testLoginUser() throws Exception {

        userRepository.update(new User("Vasya", "vasya@gmail.com", "pwd"));

        Token token = tokenService.login(new UserEmail("vasya@gmail.com"), new UserPassword("pwd"));
        assertNotNull(token);
    }

    @Test
    public void testLoginUserWithInvalidEmail() {

        try {
            tokenService.login(new UserEmail("invalid@gmail.com"), new UserPassword("pwd"));
        } catch (AuthenticationException e) {
            String result = e.getMessage();
            assertEquals("Invalid login or password.", result);
        }
    }
}
