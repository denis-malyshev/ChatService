package com.teamdev.business;

import com.teamdev.business.implement.model.ChatService;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.RepositoryFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ChatRoomServiceTest {

    RepositoryFactory repositoryFactory = new RepositoryFactory();
    ChatService service = new ChatService(repositoryFactory);
    AuthenticationToken token;
    ChatRoom chatRoom;
    User user;

    @Before
    public void setUp() throws Exception {
        token = new AuthenticationToken(0L);
        repositoryFactory.getTokenRepository().update(token);
        user = new User("Vasya", "vasya@gmail.com", "pa$$vv0rd");
        repositoryFactory.getUserRepository().update(user);
        chatRoom = new ChatRoom("freeRoom");
        service.chatRoomService.create(chatRoom);
    }

    @Test
    public void createChat() throws Exception {
        ChatRoom chat = new ChatRoom("testChat");
        service.chatRoomService.create(chat);
        ChatRoom chatRoom = repositoryFactory.getChatRoomRepository().findById(chat.getId());
        assertNotNull(chatRoom);
    }

    @Test
    public void sendMessageToChat() throws Exception {
        service.chatRoomService.sendMessage(token, "Hello", user.getId(), chatRoom.getId());
        int actual = chatRoom.getMessages().size();
        assertEquals("Count of messages must be 1", 1, actual);
    }

    @Test
    public void joinUserToChat() throws Exception {
        service.chatRoomService.joinToChatRoom(token, user.getId(), chatRoom.getId());
        int actual = chatRoom.getUsers().size();
        assertEquals("Count of users must be 1", 1, actual);
    }

    @Test
    public void leaveFromChat() throws Exception {
        joinUserToChat();
        service.chatRoomService.leaveChatRoom(token, user.getId(), chatRoom.getId());
        int actual = chatRoom.getUsers().size();
        assertEquals("Count of users must be 0", 0, actual);
    }
}
