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
    private AuthenticationServiceImpl authenticationService;
    private long count = 0;

    public UserServiceImpl(UserRepository userRepository, MessageRepository messageRepositoryImpl, AuthenticationServiceImpl authenticationService) {
        this.userRepository = userRepository;
        this.messageRepositoryImpl = messageRepositoryImpl;
        this.authenticationService = authenticationService;
    }

    public void register(User user) {
        user.setId(count++);
        userRepository.update(user);
    }

    public void sendPrivateMessage(AuthenticationToken token, String text, User sender, User receiver) throws Exception {

        authenticationService.isValid(token);

        final Message message = new Message(text, sender, receiver);
        messageRepositoryImpl.update(message);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

}
