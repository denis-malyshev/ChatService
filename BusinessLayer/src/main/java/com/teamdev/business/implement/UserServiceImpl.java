package com.teamdev.business.implement;

import com.teamdev.business.UserService;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.MessageRepository;
import com.teamdev.persistence.repository.UserRepository;

public class UserServiceImpl implements UserService<User, AuthenticationToken> {

    private UserRepository repository = new UserRepository();
    private MessageRepository messageRepository;
    private AuthenticationServiceImpl authenticationService = new AuthenticationServiceImpl();
    private long count = 0;

    public UserServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;

    }

    public boolean register(User user) {
        user.setId(count++);
        repository.update(user);
        return true;
    }

    public AuthenticationToken login(String userMail, long userId) {
        final AuthenticationToken token = authenticationService.generateToken(userId);
        repository.findById(userId).setTokenId(token.getId());
        return token;
    }

    public void sendPrivateMessage(AuthenticationToken token, String text, long senderId, long receiverId) {
        if (!authenticationService.isValid(token)) {

        }

        final Message message = new Message(text, senderId, receiverId);
        messageRepository.update(message);
    }

    public UserRepository getRepository() {
        return repository;
    }

}
