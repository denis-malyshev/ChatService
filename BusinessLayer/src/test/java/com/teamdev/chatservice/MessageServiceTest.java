package com.teamdev.chatservice;

import com.teamdev.business.AuthenticationService;
import com.teamdev.business.MessageService;
import com.teamdev.business.UserService;
import com.teamdev.business.impl.application.ApplicationConfig;
import com.teamdev.business.impl.dto.UserDTO;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.impl.exception.ChatRoomNotFoundException;
import com.teamdev.business.impl.exception.UserNotFoundException;
import com.teamdev.business.tinytypes.*;
import com.teamdev.persistence.ChatRoomRepository;
import com.teamdev.persistence.MessageRepository;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class MessageServiceTest {

    ApplicationContext context;
    MessageService messageService;
    MessageRepository messageRepository;
    UserService userService;
    ChatRoomRepository chatRoomRepository;

    UserId senderId;
    UserId recipientId;

    ChatRoomId chatRoomId;

    Token token;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        messageService = context.getBean(MessageService.class);
        messageRepository = context.getBean(MessageRepository.class);
        userService = context.getBean(UserService.class);
        chatRoomRepository = context.getBean(ChatRoomRepository.class);

        ChatRoom chatRoom = new ChatRoom("test-chat");
        chatRoomRepository.update(chatRoom);
        chatRoomId = new ChatRoomId(chatRoom.getId());

        User user1 = new User("Vasya", "vasya@gmail.com", "pwd1");
        User user2 = new User("Masha", "masha@gmail.com", "pwd");

        UserDTO userDTO1 = userService.register(
                new UserName(user1.getFirstName()),
                new UserEmail(user1.getEmail()),
                new UserPassword(user1.getPassword()));

        UserDTO userDTO2 = userService.register(
                new UserName(user2.getFirstName()),
                new UserEmail(user2.getEmail()),
                new UserPassword(user2.getPassword()));

        senderId = new UserId(userDTO1.getId());
        recipientId = new UserId(userDTO2.getId());

        token = context.getBean(AuthenticationService.class).login(new UserEmail(userDTO1.getEmail()), new UserPassword("pwd1"));
    }

    @Test
    public void testSendMessage_MessageRepositoryCanNotBeEmpty() throws Exception {

        messageService.sendMessage(token, senderId, chatRoomId, "Hello, Masha!");
        boolean result = messageRepository.findAll().isEmpty();
        assertFalse(result);
    }

    @Test
    public void testSendPrivateMessage_MessageRepositoryCanNotBeEmpty() throws Exception {

        messageService.sendPrivateMessage(token, senderId, recipientId, "Hello, Masha!");
        boolean result = messageRepository.findAll().isEmpty();
        assertFalse(result);
    }

    @Test
    public void testSendPrivateMessage_ChatRoomRepositoryCanNotBeEmpty() throws Exception {

        messageService.sendPrivateMessage(token, senderId, recipientId, "Hello, Masha!");
        boolean result = chatRoomRepository.findAll().isEmpty();
        assertFalse(result);
    }

    @Test
    public void testSendMessageToNotExistingChat() {

        try {
            messageService.sendMessage(token, senderId, new ChatRoomId(999L), "Hello, Masha!");
        } catch (AuthenticationException | UserNotFoundException | ChatRoomNotFoundException e) {
            String result = e.getMessage();
            assertEquals("ChatRoom with this id [999] not exists.", result);
        }
    }

    @Test
    public void testSendMessageToNotExistingUser() {

        try {
            messageService.sendPrivateMessage(token, senderId, new UserId(999L), "Hello, Masha!");
        } catch (AuthenticationException | UserNotFoundException e) {
            String result = e.getMessage();
            assertEquals("User with this id [999] not exists.", result);
        }
    }
}