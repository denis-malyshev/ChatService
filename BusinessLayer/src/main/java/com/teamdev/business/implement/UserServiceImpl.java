package com.teamdev.business.implement;

import com.teamdev.business.AuthenticationService;
import com.teamdev.business.MessageService;
import com.teamdev.business.UserService;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.MessageRepository;
import com.teamdev.persistence.repository.RepositoryFactory;
import com.teamdev.persistence.repository.UserRepository;

public class UserServiceImpl implements UserService<User, AuthenticationToken> {

    private UserRepository userRepository;
    private MessageRepository messageRepositoryImpl;
    private AuthenticationService authenticationService;
    private MessageService messageService;
    private long count = 0;

    public UserServiceImpl(RepositoryFactory repositoryFactory, AuthenticationService authenticationService, MessageService messageService) {
        this.messageService = messageService;
        this.userRepository = repositoryFactory.getUserRepository();
        this.messageRepositoryImpl = repositoryFactory.getMessageRepository();
        this.authenticationService = authenticationService;
    }

    public void register(User user) {
        user.setId(count++);
        userRepository.update(user);
    }

    public void sendPrivateMessage(AuthenticationToken token, String text, User sender, User receiver) throws Exception {

        authenticationService.isValid(token);

        final Message message = new Message(text, sender, receiver);

        messageService.register(message);

        sender.getMessages().add(message);
        receiver.getMessages().add(message);
    }
}
