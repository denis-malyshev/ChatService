package com.teamdev.webapp;

import com.teamdev.business.impl.AuthenticationServiceImpl;
import com.teamdev.business.impl.ChatRoomServiceImpl;
import com.teamdev.business.impl.MessageServiceImpl;
import com.teamdev.business.impl.UserServiceImpl;
import com.teamdev.business.impl.model.ApplicationConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServiceProvider {
    private static ServiceProvider ourInstance = new ServiceProvider();

    private UserServiceImpl userService;
    private ChatRoomServiceImpl chatRoomService;
    private AuthenticationServiceImpl tokenService;
    private MessageServiceImpl messageService;

    public static ServiceProvider getInstance() {
        return ourInstance;
    }

    private ServiceProvider() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ApplicationConfig.class);
        context.refresh();
        userService = (UserServiceImpl) context.getBean("userService");
        chatRoomService = (ChatRoomServiceImpl) context.getBean("chatService");
        tokenService = (AuthenticationServiceImpl) context.getBean("authenticationService");
        messageService = (MessageServiceImpl) context.getBean("messageService");
    }

    public UserServiceImpl getUserService() {
        return userService;
    }

    public ChatRoomServiceImpl getChatRoomService() {
        return chatRoomService;
    }

    public AuthenticationServiceImpl getTokenService() {
        return tokenService;
    }

    public MessageServiceImpl getMessageService() {
        return messageService;
    }
}
