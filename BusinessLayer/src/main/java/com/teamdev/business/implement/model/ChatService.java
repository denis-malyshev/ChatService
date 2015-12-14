package com.teamdev.business.implement.model;

import com.teamdev.business.implement.AuthenticationServiceImpl;
import com.teamdev.business.implement.ChatRoomServiceImpl;
import com.teamdev.business.implement.UserServiceImpl;
import com.teamdev.persistence.repository.MessageRepository;

public class ChatService {

    private UserServiceImpl userService;
    private ChatRoomServiceImpl chatRoomService;
    private AuthenticationServiceImpl authenticationService;
    private MessageRepository messageRepository;

    public ChatService() {
        messageRepository = new MessageRepository();
        userService = new UserServiceImpl(messageRepository);
        chatRoomService = new ChatRoomServiceImpl(messageRepository);
        authenticationService = new AuthenticationServiceImpl();
    }

    public static void main(String[] args) {
    }
}
