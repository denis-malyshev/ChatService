package com.teamdev.chatservice;

import com.teamdev.business.ChatRoomService;
import com.teamdev.business.impl.application.ApplicationConfig;
import com.teamdev.business.impl.dto.ChatRoomDTO;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.impl.exception.ChatRoomAlreadyExistsException;
import com.teamdev.business.impl.exception.ChatRoomNotFoundException;
import com.teamdev.business.impl.exception.UserNotFoundException;
import com.teamdev.business.tinytypes.ChatRoomId;
import com.teamdev.business.tinytypes.Token;
import com.teamdev.business.tinytypes.UserId;
import com.teamdev.persistence.AuthenticationTokenRepository;
import com.teamdev.persistence.ChatRoomRepository;
import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

public class ChatRoomServiceTest {

    private ChatRoomService chatRoomService;
    private ChatRoomRepository chatRoomRepository;
    private User user;
    private UserId userId;
    private Token token;
    private ChatRoomId chatRoomId;

    @Before
    public void setUp() throws Exception {

        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        chatRoomService = context.getBean(ChatRoomService.class);
        chatRoomRepository = context.getBean(ChatRoomRepository.class);
        UserRepository userRepository = context.getBean(UserRepository.class);
        AuthenticationTokenRepository tokenRepository = context.getBean(AuthenticationTokenRepository.class);

        user = new User("Vasya", "vasya@gmail.com", "pwd");
        userRepository.update(user);
        userId = new UserId(user.getId());

        AuthenticationToken authToken = new AuthenticationToken(userId.id);
        tokenRepository.update(authToken);
        token = new Token(authToken.getKey());

        ChatRoom chatRoom = new ChatRoom("chat-1");
        chatRoomRepository.update(chatRoom);
        chatRoomId = new ChatRoomId(chatRoom.getId());
    }

    @Test
    public void testCreateChat() throws Exception {

        ChatRoomDTO chatRoomDTO = chatRoomService.create("chat");
        assertNotNull("ChatRoomDTO must exists.", chatRoomDTO);
    }

    @Test
    public void testCreateChatWithExistingName() {

        try {
            chatRoomService.create("chat-1");
            fail();
        } catch (ChatRoomAlreadyExistsException e) {
            String result = e.getMessage();
            assertEquals("Exception message must be correct.", "ChatRoom with the same name already exists.", result);
        }
    }

    @Test
    public void testJoinUserToEmptyChat() throws Exception {

        chatRoomService.joinToChatRoom(token, userId, chatRoomId);
        int result = chatRoomRepository.findById(chatRoomId.id).getUsers().size();
        assertEquals("The count of users must be 1", 1, result);
    }

    @Test
    public void testJoinUserToNotExistingChat() {

        try {
            chatRoomService.joinToChatRoom(token, userId, new ChatRoomId(34876L));
            fail();
        } catch (AuthenticationException | UserNotFoundException | ChatRoomNotFoundException e) {
            String result = e.getMessage();
            assertEquals("Exception message must be correct.", "ChatRoom with this id [34876] not exists.", result);
        }
    }

    @Test
    public void testDeleteUserFromChat() throws Exception {

        chatRoomRepository.findById(chatRoomId.id).getUsers().add(user);

        chatRoomService.leaveChatRoom(token, userId, chatRoomId);
        boolean result = chatRoomRepository.findById(chatRoomId.id).getUsers().isEmpty();
        assertTrue("The count of users in chatRoom must be 0", result);
    }
}
