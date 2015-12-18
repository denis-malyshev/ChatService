package com.teamdev.business;

import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.business.implement.model.ChatService;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.RepositoryFactory;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ExceptionTest {

    RepositoryFactory repositoryFactory;
    ChatService service;
    AuthenticationToken validToken;
    AuthenticationToken invalidToken = new AuthenticationToken(1L);
    ChatRoom chatRoom;
    User user;

    @Before
    public void setUp() throws Exception {
        repositoryFactory = new RepositoryFactory();
        service = new ChatService(repositoryFactory);
        validToken = new AuthenticationToken(0L);
        repositoryFactory.getTokenRepository().update(validToken);
        user = new User("Vasya", "pa$$vv0rd", "vasya@gmail.com");
        service.userService.register(user);
        chatRoom = new ChatRoom("freeRoom");
        service.chatRoomService.create(chatRoom);
    }

    @Test
    public void errorMessageForInvalidTokenKeyTest() {
        try {
            service.authenticationService.isValid(invalidToken);
            fail();
        } catch (Exception e) {
            assertEquals("Invalid token key.", e.getMessage());
        }
    }

    @Test
    public void errorMessageForExpiredToken() {
        invalidToken = validToken;
        invalidToken.setExpirationTime(LocalDateTime.now().minusMinutes(1));
        try {
            service.authenticationService.isValid(invalidToken);
            fail();
        } catch (AuthenticationError e) {
            assertEquals("Token has been expired.", e.getMessage());
        }
    }

    @Test
    public void errorMessageForRegisterUserWithExistingMail() {
        try {
            service.userService.register(user);
            fail();
        } catch (AuthenticationError e) {
            assertEquals("User with the same mail already exists.", e.getMessage());
        }
    }

    @Test(expected = AuthenticationError.class)
    public void authenticationErrorTest() throws AuthenticationError {
        service.authenticationService.isValid(invalidToken);
    }
}
