package com.teamdev.business;

import com.teamdev.business.implement.AuthenticationServiceImpl;
import com.teamdev.persistence.dom.AuthenticationToken;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthenticationServiceTest {

    private AuthenticationServiceImpl authenticationService;

//    @Before
//    public void setUp() throws Exception {
//        authenticationService = new AuthenticationServiceImpl();
//    }
//
//    @Test
//    public void actualTokenValidation() throws Exception {
//        AuthenticationToken token = new AuthenticationToken(0L);
//        final boolean valid = authenticationService.isValid(token);
//        assertTrue("The token must be valid", valid);
//    }
//
//    @Test
//    public void expiredTokenValidation() throws Exception {
//        AuthenticationToken token = new AuthenticationToken(0L);
//        token.setExpirationTime(LocalDateTime.now().minusMinutes(1));
//        final boolean valid = authenticationService.isValid(token);
//        assertFalse("The token must be valid", valid);
//    }
}
