package com.teamdev.persistence.repository;

import com.teamdev.persistence.AuthenticationTokenRepository;
import com.teamdev.persistence.ChatRoomRepository;
import com.teamdev.persistence.MessageRepository;
import com.teamdev.persistence.UserRepository;

public final class RepositoryFactory {

    private final UserRepository userRepository = new UserRepositoryImpl();
    private final MessageRepository messageRepository = new MessageRepositoryImpl();
    private final ChatRoomRepository chatRoomRepository = new ChatRoomRepositoryImpl();
    private final AuthenticationTokenRepository tokenRepository = new AuthenticationTokenRepositoryImpl();

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
