package com.teamdev.chatservice;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.teamdev.business.AuthenticationService;
import com.teamdev.business.impl.application.ApplicationConfig;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.tinytypes.Token;
import com.teamdev.business.tinytypes.UserEmail;
import com.teamdev.business.tinytypes.UserPassword;
import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.charset.Charset;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class AuthenticationServerTest {

    private AuthenticationService tokenService;
    private UserRepository userRepository;

    private HashFunction hashFunction = Hashing.md5();


    @Before
    public void setUp() throws Exception {

        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        tokenService = context.getBean(AuthenticationService.class);
        userRepository = context.getBean(UserRepository.class);
    }

    @Test
    public void testLoginUser() throws Exception {

        String passwordHash = hashFunction.newHasher().putString("pwd", Charset.defaultCharset()).hash().toString();

        userRepository.update(new User("Vasya", "vasya@gmail.com", passwordHash));

        Token token = tokenService.login(new UserEmail("vasya@gmail.com"), new UserPassword("pwd"));
        assertNotNull("Token must exists.", token);
    }

    @Test
    public void testLoginUserWithInvalidEmail() {

        try {
            tokenService.login(new UserEmail("invalid@gmail.com"), new UserPassword("pwd"));
        } catch (AuthenticationException e) {
            String result = e.getMessage();
            assertEquals("Exception message must be correct.", "Invalid login or password.", result);
        }
    }
}
