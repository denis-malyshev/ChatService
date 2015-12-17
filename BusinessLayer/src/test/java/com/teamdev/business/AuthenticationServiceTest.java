package com.teamdev.business;

import com.teamdev.business.implement.model.ChatService;
import com.teamdev.persistence.dom.AuthenticationToken;
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
        service = new ChatService(new RepositoryFactory());
    }

    @Test
    public void actualTokenValidation() throws Exception {
        AuthenticationToken token = new AuthenticationToken(0L);
        service.authenticationService.getTokenRepository().update(token);
        final boolean valid = service.authenticationService.isValid(token);
        assertTrue("The token must be valid", valid);
    }

    @Test
    public void expiredTokenValidation() throws Exception {
        AuthenticationToken token = new AuthenticationToken(0L);
        service.authenticationService.getTokenRepository().update(token);
        token.setExpirationTime(LocalDateTime.now().minusMinutes(1));
        final boolean valid = service.authenticationService.isValid(token);
        assertFalse("The token must be not valid", valid);
    }
}
