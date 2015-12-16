package com.teamdev.persistence.repository;

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
