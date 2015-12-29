package com.teamdev.business.implement.model;

import com.teamdev.business.implement.AuthenticationServiceImpl;
import com.teamdev.business.implement.ChatRoomServiceImpl;
import com.teamdev.business.implement.MessageServiceImpl;
import com.teamdev.business.implement.UserServiceImpl;
import com.teamdev.persistence.repository.AuthenticationTokenRepositoryImpl;
import com.teamdev.persistence.repository.ChatRoomRepositoryImpl;
import com.teamdev.persistence.repository.MessageRepositoryImpl;
import com.teamdev.persistence.repository.UserRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public UserServiceImpl userService() {
        return new UserServiceImpl(userRepository());
    }

    @Bean
    public MessageServiceImpl messageService() {
        return new MessageServiceImpl(messageRepository(),userRepository(),tokenService());
    }

    @Bean
    public ChatRoomServiceImpl chatService() {
        return new ChatRoomServiceImpl(chatRoomRepository(),userRepository(),tokenService());
    }

    @Bean
    public AuthenticationServiceImpl tokenService() {
        return new AuthenticationServiceImpl(tokenRepository(),messageRepository());
    }

    @Bean
    public UserRepositoryImpl userRepository() {
        return new UserRepositoryImpl();
    }

    @Bean
    public MessageRepositoryImpl messageRepository() {
        return new MessageRepositoryImpl();
    }

    @Bean
    public ChatRoomRepositoryImpl chatRoomRepository() {
        return new ChatRoomRepositoryImpl();
    }

    @Bean
    public AuthenticationTokenRepositoryImpl tokenRepository() {
        return new AuthenticationTokenRepositoryImpl();
    }

}
