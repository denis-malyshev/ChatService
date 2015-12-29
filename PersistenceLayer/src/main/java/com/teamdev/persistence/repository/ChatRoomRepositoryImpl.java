package com.teamdev.persistence.repository;

import com.teamdev.persistence.ChatRoomRepository;
import com.teamdev.persistence.dom.ChatRoom;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ChatRoomRepositoryImpl implements ChatRoomRepository {

    private Map<Long, ChatRoom> chatRooms = new HashMap<>();
    private long id = 1;

    public ChatRoomRepositoryImpl() {
    }

    public ChatRoom findById(long id) {
        return chatRooms.get(id);
    }

    @Override
    public ChatRoom findByName(String name) {
        return chatRooms.values().stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
    }

    public Collection<ChatRoom> findAll() {
        return chatRooms.values();
    }

    public void update(ChatRoom chatRoom) {
        if (chatRoom.getId() == 0) {
            chatRoom.setId(id++);
        }
        chatRooms.put(chatRoom.getId(), chatRoom);
    }

    public void delete(long id) {
        chatRooms.remove(id);
    }
}
