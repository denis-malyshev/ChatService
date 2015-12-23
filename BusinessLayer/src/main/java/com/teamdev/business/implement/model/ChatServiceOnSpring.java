package com.teamdev.business.implement.model;

import com.teamdev.business.implement.AuthenticationServiceImpl;
import com.teamdev.business.implement.ChatRoomServiceImpl;
import com.teamdev.business.implement.MessageServiceImpl;
import com.teamdev.business.implement.UserServiceImpl;
import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.dom.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ChatServiceOnSpring {

    public UserServiceImpl userService;
    public ChatRoomServiceImpl chatRoomService;
    public AuthenticationServiceImpl tokenService;
    public MessageServiceImpl messageService;

    public ChatServiceOnSpring() {

        ApplicationContext businessContext = new ClassPathXmlApplicationContext("/META-INF/beansBusiness.xml");
        userService = (UserServiceImpl) businessContext.getBean("userService");
        chatRoomService = (ChatRoomServiceImpl) businessContext.getBean("chatService");
        tokenService = (AuthenticationServiceImpl) businessContext.getBean("tokenService");
        messageService = (MessageServiceImpl) businessContext.getBean("messageService");

    }

    public static void main(String[] args) throws AuthenticationError {

        ChatServiceOnSpring service = new ChatServiceOnSpring();

        service.userService.register(new User("vasya", "vasya@gmail.com", "pwd"));
    }
}
