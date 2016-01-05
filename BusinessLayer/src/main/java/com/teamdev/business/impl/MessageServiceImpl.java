package com.teamdev.business.impl;

import com.teamdev.business.MessageService;
import com.teamdev.business.impl.dto.ChatRoomId;
import com.teamdev.business.impl.dto.MessageDTO;
import com.teamdev.business.impl.dto.Token;
import com.teamdev.business.impl.dto.UserId;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.impl.exception.ChatRoomNotFoundException;
import com.teamdev.business.impl.exception.UserNotFoundException;
import com.teamdev.persistence.ChatRoomRepository;
import com.teamdev.persistence.MessageRepository;
import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.dom.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public MessageServiceImpl() {
    }

    @Override
    public MessageDTO sendMessage(Token token, UserId userId, ChatRoomId chatRoomId, String text)
            throws AuthenticationException, UserNotFoundException, ChatRoomNotFoundException {

        User user = userRepository.findById(userId.id);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId.id);

        if (user == null) {
            throw new UserNotFoundException(String.format("User with this id [%d] not exists.", userId.id));
        }

        if (chatRoom == null) {
            throw new ChatRoomNotFoundException(String.format("ChatRoom with this id [%d] not exists.", chatRoomId.id));
        }

        Message message = new Message(text, user, chatRoom);

        messageRepository.update(message);

        user.getMessages().add(message);
        chatRoom.getMessages().add(message);

        return new MessageDTO(message.getId(), message.getText(), message.getTime());
    }

    @Override
    public MessageDTO sendPrivateMessage(Token token, UserId senderId, UserId receiverId, String text)
            throws AuthenticationException, UserNotFoundException {

        User sender = userRepository.findById(senderId.id);
        User receiver = userRepository.findById(receiverId.id);

        if (sender == null) {
            throw new UserNotFoundException(String.format("User with this id [%d] not exists.", senderId.id));
        }

        if (receiver == null) {
            throw new UserNotFoundException(String.format("User with this id [%d] not exists.", receiverId.id));
        }

        Message message = new Message(text, sender, receiver);
        messageRepository.update(message);

        sender.getMessages().add(message);
        receiver.getMessages().add(message);

        return new MessageDTO(message.getId(), message.getText(), message.getTime());
    }
}
