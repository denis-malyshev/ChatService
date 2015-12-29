package com.teamdev.business.impl;

import com.teamdev.business.ChatRoomService;
import com.teamdev.business.impl.dto.ChatRoomDTO;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.persistence.ChatRoomRepository;
import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.ChatRoom;
import com.teamdev.persistence.dom.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@Service("chatService")
public class ChatRoomServiceImpl implements ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationServiceImpl authenticationService;

    public ChatRoomServiceImpl() {
    }

    public ChatRoomDTO create(ChatRoom chatRoom) {
        chatRoomRepository.update(chatRoom);
        return new ChatRoomDTO(chatRoom.getId(), chatRoom.getName(), 0, 0);
    }

    public void joinToChatRoom(String token,
                               long userId, long chatRoomId) throws AuthenticationException {

        authenticationService.validation(token);

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);
        User user = userRepository.findById(userId);

        user.getChatRooms().add(chatRoom);
        chatRoom.getUsers().add(user);
    }

    public void leaveChatRoom(String token,
                              long userId, long chatRoomId) throws AuthenticationException {

        authenticationService.validation(token);

        User user = userRepository.findById(userId);
        if (user == null) {
            return;
        }
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);

        user.getChatRooms().remove(chatRoom);
        chatRoom.getUsers().remove(user);
    }

    @Override
    public Collection<ChatRoomDTO> findAll() {
        Collection<ChatRoom> chatRooms = chatRoomRepository.findAll();
        Collection<ChatRoomDTO> chatRoomDTOs = new ArrayList<>();
        Iterator<ChatRoom> iterator = chatRooms.iterator();
        while (iterator.hasNext()) {
            ChatRoom chat = iterator.next();
            chatRoomDTOs.add(new ChatRoomDTO(chat.getId(), chat.getName(), chat.getUsers().size(), chat.getMessages().size()));
        }
        return chatRoomDTOs;
    }
}
