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

    RepositoryFactory repositoryFactory = new RepositoryFactory();
    ChatService service = new ChatService(repositoryFactory);
    AuthenticationToken validToken;
    AuthenticationToken invalidToken = new AuthenticationToken(1L);
    ChatRoom chatRoom;
    User user1;
    User user2;

    @Before
    public void setUp() throws Exception {
        validToken = new AuthenticationToken(0L);
        repositoryFactory.getTokenRepository().update(validToken);
        user1 = new User("Vasya", "pa$$vv0rd", "vasya@gmail.com");
        user2 = new User("Masha", "pwd123", "masha@gmail.com");
        repositoryFactory.getUserRepository().update(user1);
        repositoryFactory.getUserRepository().update(user2);
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
        } catch (Exception e) {
            assertEquals("Token has been expired.", e.getMessage());
        }
    }

    @Test(expected = AuthenticationError.class)
    public void authenticationErrorTest() throws AuthenticationError {
        service.authenticationService.isValid(invalidToken);
    }
}
