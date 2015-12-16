package com.teamdev.business.implement.model;


import com.teamdev.business.implement.AuthenticationServiceImpl;
import com.teamdev.business.implement.ChatRoomServiceImpl;
import com.teamdev.business.implement.UserServiceImpl;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.MessageRepositoryImpl;
import com.teamdev.persistence.repository.RepositoryFactory;

import java.io.IOException;

public class ChatService {

    private UserServiceImpl userService;
    private ChatRoomServiceImpl chatRoomService;
    private AuthenticationServiceImpl authenticationService;
    private MessageRepositoryImpl messageRepositoryImpl;
    private final RepositoryFactory repositoryFactory = new RepositoryFactory();

    public ChatService() {
        messageRepositoryImpl = new MessageRepositoryImpl();
        userService = new UserServiceImpl(repositoryFactory.getUserRepository(),
                repositoryFactory.getMessageRepository());
        chatRoomService = new ChatRoomServiceImpl(repositoryFactory.getMessageRepository());
        authenticationService = new AuthenticationServiceImpl();
    }

    public static void main(String[] args) throws IOException {
        ChatService service = new ChatService();
        service.userService.register(new User("Denis", "jsdghfiuygiyusdgf", "denis@mail.ru"));
        System.out.println(service.userService.getUserRepository().findById(0).getPassword());
    }
}
