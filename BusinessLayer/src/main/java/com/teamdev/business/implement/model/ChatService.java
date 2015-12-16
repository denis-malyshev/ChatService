package com.teamdev.business.implement.model;


import com.teamdev.business.implement.AuthenticationServiceImpl;
import com.teamdev.business.implement.ChatRoomServiceImpl;
import com.teamdev.business.implement.UserServiceImpl;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.MessageRepositoryImpl;
import com.teamdev.persistence.repository.RepositoryFactory;

public class ChatService {

    private final RepositoryFactory repositoryFactory = new RepositoryFactory();

    private UserServiceImpl userService;
    private ChatRoomServiceImpl chatRoomService;
    private AuthenticationServiceImpl authenticationService;
    private MessageRepositoryImpl messageRepositoryImpl;

    public ChatService() {
        messageRepositoryImpl = new MessageRepositoryImpl();
        userService = new UserServiceImpl(repositoryFactory.getUserRepository(),
                repositoryFactory.getMessageRepository(), authenticationService);
        chatRoomService = new ChatRoomServiceImpl(repositoryFactory.getMessageRepository(), authenticationService);
        authenticationService = new AuthenticationServiceImpl(repositoryFactory.getTokenRepository(),
                repositoryFactory.getUserRepository());
    }

    public static void main(String[] args) throws Exception {
        ChatService service = new ChatService();
        service.userService.register(new User("Denis", "jsdghfiuygiyusdgf", "denis@mail.ru"));
        System.out.println(service.userService.getUserRepository().findById(0).getPassword());
    }
}
