package com.teamdev.business;

import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.business.implement.model.ChatService;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.exception.EntityNotFoundException;
import com.teamdev.persistence.repository.RepositoryFactory;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ExceptionTest {

    RepositoryFactory repositoryFactory = new RepositoryFactory();
    ChatService service = new ChatService(repositoryFactory);
    AuthenticationToken token;
    ChatRoom chatRoom;
    User user1;
    User user2;

    @Before
    public void setUp() throws Exception {
        token = new AuthenticationToken(0L);
        repositoryFactory.getTokenRepository().update(token);
        user1 = new User("Vasya", "pa$$vv0rd", "vasya@gmail.com");
        user2 = new User("Masha", "pwd123", "masha@gmail.com");
        repositoryFactory.getUserRepository().update(user1);
        repositoryFactory.getUserRepository().update(user2);
        chatRoom = new ChatRoom("freeRoom");
        service.chatRoomService.register(chatRoom);
    }

    @Test
    public void errorMessageForInvalidTokenKeyTest() {
        token.setKey("3745698");
        try {
            service.authenticationService.isValid(token);
            fail();
        } catch (Exception e) {
            assertEquals("Invalid token key.", e.getMessage());
        }
    }

    @Test
    public void errorMessageForExpiredToken(){
        token.setExpirationTime(LocalDateTime.now().plusMinutes(1));
        try {
            service.authenticationService.isValid(token);
            fail();
        } catch (Exception e) {
            assertEquals("Token has been expired.", e.getMessage());
        }
    }

    @Test(expected = AuthenticationError.class)
    public void authenticationErrorTest() throws AuthenticationError, EntityNotFoundException {
        token.setExpirationTime(LocalDateTime.now().plusMinutes(1));
        repositoryFactory.getTokenRepository().update(token);
        service.authenticationService.isValid(token);
    }
}
