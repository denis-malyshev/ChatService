package com.teamdev.business;

import com.teamdev.business.implement.model.ChatService;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.RepositoryFactory;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthenticationServiceTest {

    private ChatService service;

    @Before
    public void setUp() throws Exception {
        RepositoryFactory repositoryFactory = new RepositoryFactory();
        service = new ChatService(repositoryFactory);
        service.userService.register(new User("vasya","vasya@gmail.com","asd123"));
    }

    @Test
    public void loginUserTest() throws Exception {
        service.authenticationService.login("vasya@gmail.com","asd123");
    }

    @Test
    public void actualTokenValidation() throws Exception {
        AuthenticationToken token = new AuthenticationToken(0L);
        service.authenticationService.getTokenRepository().update(token);
        final boolean valid = service.authenticationService.isValid(token);
        assertTrue("The invalidToken must be valid", valid);
    }
}
