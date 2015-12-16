package com.teamdev.business.implement;

import com.teamdev.business.UserService;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.MessageRepository;
import com.teamdev.persistence.repository.UserRepository;

public class UserServiceImpl implements UserService<User, AuthenticationToken> {

    private UserRepository userRepository;
    private MessageRepository messageRepositoryImpl;
    private AuthenticationServiceImpl authenticationService = new AuthenticationServiceImpl();
    private long count = 0;

    public UserServiceImpl(UserRepository userRepository, MessageRepository messageRepositoryImpl) {
        this.userRepository = userRepository;
        this.messageRepositoryImpl = messageRepositoryImpl;
    }

    public boolean register(User user) {
        user.setId(count++);
        userRepository.update(user);
        return true;
    }

    public AuthenticationToken login(String userMail, long userId) {
        final AuthenticationToken token = authenticationService.generateToken(userId);
        userRepository.findById(userId).setTokenId(token.getId());
        return token;
    }

    public void sendPrivateMessage(AuthenticationToken token, String text, long senderId, long receiverId) {
        if (!authenticationService.isValid(token)) {
        }

        final Message message = new Message(text, senderId, receiverId);
        messageRepositoryImpl.update(message);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

}
