package com.teamdev.business.implement;

import com.teamdev.business.ChatRoomService;
import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.dom.AuthenticationToken;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.ChatRoomRepository;
import com.teamdev.persistence.repository.RepositoryFactory;
import com.teamdev.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomServiceImpl implements ChatRoomService<AuthenticationToken> {

    @Autowired
    private ChatRoomRepository repository;
    @Autowired
    private UserRepository userRepository;
    private MessageServiceImpl messageService;
    private AuthenticationServiceImpl authenticationService;
    private long count = 0;

    public ChatRoomServiceImpl(RepositoryFactory repositoryFactory,
                               AuthenticationServiceImpl authenticationService, MessageServiceImpl messageService) {
        this.repository = repositoryFactory.getChatRoomRepository();
        this.authenticationService = authenticationService;
        this.messageService = messageService;
        this.userRepository = repositoryFactory.getUserRepository();
    }

    public boolean create(ChatRoom chatRoom) {
        chatRoom.setId(count++);
        repository.update(chatRoom);
        return true;
    }

    public void joinToChatRoom(AuthenticationToken token,
                               long userId, long chatRoomId) throws AuthenticationError {

        authenticationService.isValid(token);

        User user = userRepository.findById(userId);

        repository.findById(chatRoomId).getUsers().add(user);
    }

    public void sendMessage(AuthenticationToken token, String text,
                            long userId, long chatRoomId) throws AuthenticationError {

        authenticationService.isValid(token);

        User user = userRepository.findById(userId);
        ChatRoom chatRoom = repository.findById(chatRoomId);

        Message message = new Message(text, user, chatRoom);

        messageService.register(message);

        repository.findById(chatRoom.getId()).getMessages().add(message);
    }

    public void leaveChatRoom(AuthenticationToken token,
                              long userId, long chatRoomId) throws AuthenticationError {

        authenticationService.isValid(token);

        User user = userRepository.findById(userId);

        repository.findById(chatRoomId).getUsers().remove(user);
    }

}
