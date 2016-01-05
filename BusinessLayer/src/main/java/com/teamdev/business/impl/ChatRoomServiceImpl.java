package com.teamdev.business.impl;

import com.teamdev.business.ChatRoomService;
import com.teamdev.business.impl.dto.ChatRoomDTO;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.impl.exception.ChatRoomAlreadyExistsException;
import com.teamdev.business.impl.exception.ChatRoomNotFoundException;
import com.teamdev.business.impl.exception.UserNotFoundException;
import com.teamdev.business.tinytypes.ChatRoomId;
import com.teamdev.business.tinytypes.Token;
import com.teamdev.business.tinytypes.UserId;
import com.teamdev.persistence.ChatRoomRepository;
import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserRepository userRepository;

    public ChatRoomServiceImpl() {
    }

    public ChatRoomDTO create(String chatRoomName) throws ChatRoomAlreadyExistsException {

        if (chatRoomRepository.chatRoomCount() > 0 && chatRoomRepository.findByName(chatRoomName) != null) {
            throw new ChatRoomAlreadyExistsException("ChatRoom with the same name already exists.");
        }

        ChatRoom chatRoom = new ChatRoom(chatRoomName);
        chatRoomRepository.update(chatRoom);
        return new ChatRoomDTO(chatRoom.getId(), chatRoom.getName(), 0, 0);
    }

    public void joinToChatRoom(Token token,
                               UserId userId,
                               ChatRoomId chatRoomId)
            throws AuthenticationException, UserNotFoundException, ChatRoomNotFoundException {

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId.id);
        User user = userRepository.findById(userId.id);

        if (user == null) {
            throw new UserNotFoundException("User with this id [" + userId.id + "] not exists.");
        }

        if (chatRoom == null) {
            throw new ChatRoomNotFoundException("ChatRoom with this id [" + chatRoomId.id + "] not exists.");
        }

        user.getChatRooms().add(chatRoom);
        chatRoom.getUsers().add(user);
    }

    public void leaveChatRoom(Token token,
                              UserId userId,
                              ChatRoomId chatRoomId)
            throws AuthenticationException, ChatRoomNotFoundException {

        User user = userRepository.findById(userId.id);

        if (user == null) {
            return;
        }

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId.id);

        if (chatRoom == null) {
            throw new ChatRoomNotFoundException("ChatRoom with this id [" + chatRoomId.id + "] not exists.");
        }

        user.getChatRooms().remove(chatRoom);
        chatRoom.getUsers().remove(user);
    }

    @Override
    public Collection<ChatRoomDTO> findAll() {
        Collection<ChatRoom> chatRooms = chatRoomRepository.findAll();
        Collection<ChatRoomDTO> chatRoomDTOs = new ArrayList<>();
        for (ChatRoom chat : chatRooms) {
            chatRoomDTOs.add(new ChatRoomDTO(chat.getId(), chat.getName(), chat.getUsers().size(), chat.getMessages().size()));
        }
        return chatRoomDTOs;
    }
}
