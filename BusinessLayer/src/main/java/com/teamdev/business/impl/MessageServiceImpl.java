package com.teamdev.business.impl;

import com.teamdev.business.MessageService;
import com.teamdev.business.impl.dto.MessageDTO;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.impl.exception.ChatRoomNotFoundException;
import com.teamdev.business.impl.exception.UserNotFoundException;
import com.teamdev.business.tinytypes.ChatRoomId;
import com.teamdev.business.tinytypes.Token;
import com.teamdev.business.tinytypes.UserId;
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

        User user = userRepository.findById(userId.getId());
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId.getId());

        if (user == null) {
            throw new UserNotFoundException("User with this id [" + userId.getId() + "] not exists.");
        }

        if (chatRoom == null) {
            throw new ChatRoomNotFoundException("ChatRoom with this id [" + chatRoomId.getId() + "] not exists.");
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

        User sender = userRepository.findById(senderId.getId());
        User receiver = userRepository.findById(receiverId.getId());

        if (sender == null) {
            throw new UserNotFoundException("User with this id [" + receiverId.getId() + "] not exists.");
        }

        if (receiver == null) {
            throw new UserNotFoundException("User with this id [" + receiverId.getId() + "] not exists.");
        }

        Message message = new Message(text, sender, receiver);
        messageRepository.update(message);

        String chatRoomName = "private-room-" + senderId.getId() + receiverId.getId();
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

        return new MessageDTO(message.getId(), message.getText(), message.getTime());
    }
}
