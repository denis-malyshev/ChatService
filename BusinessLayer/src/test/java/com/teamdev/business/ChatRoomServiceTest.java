package com.teamdev.business;

import com.teamdev.business.implement.ChatRoomServiceImpl;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.repository.MessageRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChatRoomServiceTest {

    private ChatRoomServiceImpl chatRoomService;
    private AuthenticationToken token;

    @Before
    public void setUp() throws Exception {
        chatRoomService = new ChatRoomServiceImpl(new MessageRepository());
        token = new AuthenticationToken(0L);
    }

    @Test
    public void registerChat() throws Exception {
        chatRoomService.register(new ChatRoom("first"));
        final int actual = chatRoomService.getRepository().findAll().size();
        assertEquals("The chatRoom count must be 1", 1, actual);
    }

    @Test
    public void sendMessageToChat() throws Exception {
        long userId = 0;
        long chatId = 0;
        chatRoomService.register(new ChatRoom("first"));
        chatRoomService.sendMessage(token, "hello", userId, chatId);
        final int actual = chatRoomService.getRepository().findById(chatId).getMessages().size();
        assertEquals("The message count must be 1", 1, actual);
    }

    @Test
    public void joinUserToChat() throws Exception {
        long chatId = 0;
        chatRoomService.register(new ChatRoom("first"));
        chatRoomService.joinToChatRoom(token, 0L, chatId);
        final int actual = chatRoomService.getRepository().findById(chatId).getUsers().size();
        assertEquals("Count of users must be 1", 1, actual);
    }
}
