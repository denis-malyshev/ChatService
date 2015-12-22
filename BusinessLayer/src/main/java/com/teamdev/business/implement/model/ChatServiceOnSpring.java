package com.teamdev.business.implement.model;

import com.teamdev.business.implement.AuthenticationServiceImpl;
import com.teamdev.business.implement.ChatRoomServiceImpl;
import com.teamdev.business.implement.MessageServiceImpl;
import com.teamdev.business.implement.UserServiceImpl;
import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.RepositoryFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ChatServiceOnSpring {

    private ApplicationContext businessContext;
    private UserServiceImpl userService;
    private ChatRoomServiceImpl chatRoomService;
    private AuthenticationServiceImpl tokenService;
    private MessageServiceImpl messageService;

    public ChatServiceOnSpring() {

        businessContext = new ClassPathXmlApplicationContext("META-INF/beansBusiness.xml");
        userService = (UserServiceImpl) businessContext.getBean("userService");
        chatRoomService = (ChatRoomServiceImpl) businessContext.getBean("chatService");
        tokenService = (AuthenticationServiceImpl) businessContext.getBean("tokenService");
        messageService = (MessageServiceImpl) businessContext.getBean("messageService");

        RepositoryFactory factory = new RepositoryFactory();

        userService.setAuthenticationService(tokenService);
        userService.setMessageService(messageService);
        userService.setChatRoomRepository(factory.getChatRoomRepository());
        userService.setUserRepository(factory.getUserRepository());

        chatRoomService.setAuthenticationService(tokenService);
        chatRoomService.setMessageService(messageService);
        chatRoomService.setUserRepository(factory.getUserRepository());
        chatRoomService.setRepository(factory.getChatRoomRepository());

        tokenService.setUserRepository(factory.getUserRepository());
        tokenService.setTokenRepository(factory.getTokenRepository());

        messageService.setRepository(factory.getMessageRepository());
    }

    public static void main(String[] args) throws AuthenticationError {
        ChatServiceOnSpring service = new ChatServiceOnSpring();

        service.userService.register(new User("vasya", "vasya@gmail.com", "pwd"));
    }
}
