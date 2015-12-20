package com.teamdev.business.implement.model;


import com.teamdev.business.implement.AuthenticationServiceImpl;
import com.teamdev.business.implement.ChatRoomServiceImpl;
import com.teamdev.business.implement.MessageServiceImpl;
import com.teamdev.business.implement.UserServiceImpl;
import com.teamdev.persistence.repository.RepositoryFactory;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ChatService {

    private static final Logger logger = Logger.getLogger(ChatService.class);

    private final RepositoryFactory repositoryFactory;

    public UserServiceImpl userService;
    public ChatRoomServiceImpl chatRoomService;
    public AuthenticationServiceImpl authenticationService;
    public MessageServiceImpl messageService;

    public ChatService(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
        messageService = new MessageServiceImpl(repositoryFactory.getMessageRepository());
        authenticationService = new AuthenticationServiceImpl(repositoryFactory.getTokenRepository(),
                repositoryFactory.getUserRepository());
        userService = new UserServiceImpl(repositoryFactory, authenticationService, messageService);
        chatRoomService = new ChatRoomServiceImpl(repositoryFactory, authenticationService, messageService);
    }

    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/beans.xml");
        ChatService service = (ChatService) context.getBean("");
    }
}
