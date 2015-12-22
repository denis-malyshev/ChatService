package com.teamdev.business.implement;

import com.teamdev.business.AuthenticationService;
import com.teamdev.business.MessageService;
import com.teamdev.business.UserService;
import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.ChatRoomRepository;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.RepositoryFactory;
import com.teamdev.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service("userService")
public class UserServiceImpl implements UserService<AuthenticationToken> {

    private UserRepository userRepository;
    private ChatRoomRepository chatRoomRepository;
    private AuthenticationService authenticationService;
    private MessageService messageService;
    private long count = 0;

    public UserServiceImpl() {
    }

    public UserServiceImpl(RepositoryFactory repositoryFactory,
                           AuthenticationService authenticationService, MessageService messageService) {
        this.userRepository = repositoryFactory.getUserRepository();
        this.chatRoomRepository = repositoryFactory.getChatRoomRepository();
        this.authenticationService = authenticationService;
        this.messageService = messageService;
    }

    public void register(User user) throws AuthenticationError {
        if (userRepository.count() > 0)
            if (userRepository.findByMail(user.getMail()) != null) {
                throw new AuthenticationError("User with the same mail already exists.");
            }
        user.setId(count++);
        userRepository.update(user);
    }

    public void sendPrivateMessage(AuthenticationToken token, String text,
                                   long senderId, long receiverID) throws AuthenticationError {

        authenticationService.isValid(token);

        User sender = userRepository.findById(senderId);
        User receiver = userRepository.findById(receiverID);

        Message message = new Message(text, sender, receiver);
        messageService.register(message);

        StringBuilder builder = new StringBuilder();
        builder.append("private-room-").append(senderId).append(receiverID);

        String chatRoomName = builder.toString();
        ChatRoom chatRoom = chatRoomRepository.findByName(chatRoomName);
        if (chatRoom == null) {
            chatRoom = new ChatRoom(chatRoomName);
        }
        chatRoom.getUsers().add(sender);
        chatRoom.getUsers().add(receiver);
        chatRoom.getMessages().add(message);
        chatRoomRepository.update(chatRoom);

        sender.getMessages().add(message);
        receiver.getMessages().add(message);
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setChatRoomRepository(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }
}
