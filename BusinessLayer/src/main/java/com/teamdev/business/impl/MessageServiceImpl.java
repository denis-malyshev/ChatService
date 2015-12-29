package com.teamdev.business.impl;

import com.teamdev.business.AuthenticationService;
import com.teamdev.business.MessageService;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.persistence.ChatRoomRepository;
import com.teamdev.persistence.MessageRepository;
import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.dom.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private AuthenticationService authenticationService;

    public MessageServiceImpl() {
    }

    @Override
    public void sendMessage(String token, String text, long userId, long chatRoomId) throws AuthenticationException {

        authenticationService.validation(token);

        User user = userRepository.findById(userId);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);

        Message message = new Message(text, user, chatRoom);

        messageRepository.update(message);

        user.getMessages().add(message);
        chatRoom.getMessages().add(message);
    }

    @Override
    public void sendPrivateMessage(String token, String text, long senderId, long receiverId) throws AuthenticationException {

        authenticationService.validation(token);

        User sender = userRepository.findById(senderId);
        User receiver = userRepository.findById(receiverId);

        Message message = new Message(text, sender, receiver);
        messageRepository.update(message);

        StringBuilder builder = new StringBuilder();
        builder.append("private-room-").append(senderId).append(receiverId);

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
}
