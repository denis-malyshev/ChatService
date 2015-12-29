package com.teamdev.business.implement;

import com.teamdev.business.AuthenticationService;
import com.teamdev.business.MessageService;
import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.ChatRoomRepository;
import com.teamdev.persistence.MessageRepository;
import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.dom.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("messageService")
public class MessageServiceImpl implements MessageService<Message> {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private AuthenticationService authenticationService;
    private long count = 0;

    public MessageServiceImpl() {
    }

    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public void create(Message message) {
        message.setId(count++);
        messageRepository.update(message);
    }

    @Override
    public void sendMessage(String token, String text, long userId, long chatRoomId) throws AuthenticationError {

        authenticationService.isValid(token);

        User user = userRepository.findById(userId);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);

        Message message = new Message(text, user, chatRoom);

        create(message);

        user.getMessages().add(message);
        chatRoom.getMessages().add(message);
    }

    @Override
    public void sendPrivateMessage(String token, String text, long senderId, long receiverId) throws AuthenticationError {

        authenticationService.isValid(token);

        User sender = userRepository.findById(senderId);
        User receiver = userRepository.findById(receiverId);

        Message message = new Message(text, sender, receiver);
        create(message);

        StringBuilder builder = new StringBuilder();
        builder.append("private-room-").append(senderId).append(receiverId);

        String chatRoomName = builder.toString();
        ChatRoom chatRoom = chatRoomRepository.findByName(chatRoomName);
        if (chatRoom == null) {
            chatRoom = new ChatRoom(chatRoomName);
            chatRoom.setId(System.nanoTime());
        }
        chatRoom.getUsers().add(sender);
        chatRoom.getUsers().add(receiver);
        chatRoom.getMessages().add(message);
        chatRoomRepository.update(chatRoom);

        sender.getMessages().add(message);
        receiver.getMessages().add(message);
    }

    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
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
}
