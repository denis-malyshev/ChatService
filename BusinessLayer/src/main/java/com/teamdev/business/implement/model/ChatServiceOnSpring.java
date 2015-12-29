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

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/META-INF/beans.xml");
        userService = (UserServiceImpl) applicationContext.getBean("userService");
        chatRoomService = (ChatRoomServiceImpl) applicationContext.getBean("chatService");
        tokenService = (AuthenticationServiceImpl) applicationContext.getBean("tokenService");
        messageService = (MessageServiceImpl) applicationContext.getBean("messageService");

    }

    public static void main(String[] args) throws AuthenticationError {

        ChatServiceOnSpring service = new ChatServiceOnSpring();

        User vasya = new User("Vasya", "vasya@gmail.com", "pwd");
        User masha = new User("Masha", "masha@gmail.com", "pass");

        service.userService.register(vasya);
        service.userService.register(masha);

        service.tokenService.login("vasya@gmail.com", "pwd");

        service.messageService.sendPrivateMessage(vasya.getToken(),"Hello, Masha!",vasya.getId(),masha.getId());
        service.messageService.sendPrivateMessage(vasya.getToken(),"Hello, Masha!",vasya.getId(),masha.getId());

        //service.messageService.sendPrivateMessage(vasya.getToken(),"Hello, Masha!",masha.getId(),vasya.getId());

        System.out.println(service.chatRoomService.findAll().size());
    }
}
