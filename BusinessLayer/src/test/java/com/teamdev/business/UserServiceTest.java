package com.teamdev.business;

import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.business.implement.model.ChatService;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.RepositoryFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserServiceTest {

    RepositoryFactory repositoryFactory = new RepositoryFactory();
    ChatService service = new ChatService(repositoryFactory);
    AuthenticationToken token;
    ChatRoom chatRoom;

    @Before
    public void setUp() throws Exception {
        token = new AuthenticationToken(0L);
        repositoryFactory.getTokenRepository().update(token);
        chatRoom = new ChatRoom("freeRoom");
        service.chatRoomService.create(chatRoom);
    }

    @Test
    public void registerFirstUser() throws Exception {
        User user = new User("Vasya", "pa$$vv0rd", "vasya@gmail.com");
        service.userService.register(user);
        User actual = repositoryFactory.getUserRepository().findById(user.getId());
        assertNotNull("The user must exist", actual);
    }

    @Test
    public void sendPrivateMessage() throws AuthenticationError {

        User sender = new User("Vasya", "pa$$vv0rd", "vasya@gmail.com");
        User recipient = new User("Vasya", "pa$$vv0rd", "vasya@gmail.com");

        service.userService.register(sender);
        service.userService.register(recipient);
        service.userService.sendPrivateMessage(token, "Hello", sender.getId(), recipient.getId());

        int actual = repositoryFactory.getMessageRepository().count();
        assertEquals("Count of message must be 1", 1, actual);
    }
}
