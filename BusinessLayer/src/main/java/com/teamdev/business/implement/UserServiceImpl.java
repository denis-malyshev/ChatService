package com.teamdev.business.implement;

import com.teamdev.business.AuthenticationService;
import com.teamdev.business.MessageService;
import com.teamdev.business.UserService;
import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.MessageRepository;
import com.teamdev.persistence.repository.RepositoryFactory;
import com.teamdev.persistence.repository.UserRepository;

public class UserServiceImpl implements UserService<AuthenticationToken> {

    private UserRepository userRepository;
    private AuthenticationService authenticationService;
    private MessageService messageService;
    private long count = 0;

    public UserServiceImpl(RepositoryFactory repositoryFactory,
                           AuthenticationService authenticationService, MessageService messageService) {
        this.messageService = messageService;
        this.userRepository = repositoryFactory.getUserRepository();
        this.authenticationService = authenticationService;
    }

    public void register(User user) {
        user.setId(count++);
        userRepository.update(user);
    }

    public void sendPrivateMessage(AuthenticationToken token, String text,
                                   long senderId, long receiverID) throws AuthenticationError {

        authenticationService.isValid(token);

        User sender = userRepository.findById(senderId);
        User receiver = userRepository.findById(receiverID);

        final Message message = new Message(text, sender, receiver);

        messageService.register(message);

        sender.getMessages().add(message);
        receiver.getMessages().add(message);
    }
}
