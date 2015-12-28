package com.teamdev.webapp;

import com.teamdev.business.implement.AuthenticationServiceImpl;
import com.teamdev.business.implement.ChatRoomServiceImpl;
import com.teamdev.business.implement.MessageServiceImpl;
import com.teamdev.business.implement.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/META-INF/beans.xml");
        userService = (UserServiceImpl) applicationContext.getBean("userService");
        chatRoomService = (ChatRoomServiceImpl) applicationContext.getBean("chatService");
        tokenService = (AuthenticationServiceImpl) applicationContext.getBean("tokenService");
        messageService = (MessageServiceImpl) applicationContext.getBean("messageService");
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
