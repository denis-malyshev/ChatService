package com.teamdev.persistence.repository;

import com.teamdev.persistence.AuthenticationTokenRepository;
import com.teamdev.persistence.ChatRoomRepository;
import com.teamdev.persistence.MessageRepository;
import com.teamdev.persistence.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class RepositoryFactory {

    private final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/META-INF/beans.xml");
    private final UserRepository userRepository = (UserRepository) applicationContext.getBean("userRepository");
    private final MessageRepository messageRepository = (MessageRepository) applicationContext.getBean("messageRepository");
    private final ChatRoomRepository chatRoomRepository = (ChatRoomRepository) applicationContext.getBean("chatRoomRepository");
    private final AuthenticationTokenRepository tokenRepository = (AuthenticationTokenRepository) applicationContext.getBean("tokenRepository");

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public MessageRepository getMessageRepository() {
        return messageRepository;
    }

    public ChatRoomRepository getChatRoomRepository() {
        return chatRoomRepository;
    }

    public AuthenticationTokenRepository getTokenRepository() {
        return tokenRepository;
    }

}
