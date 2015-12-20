package com.teamdev.persistence.repository;

import com.teamdev.persistence.dom.ChatRoom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ChatRoomRepositoryImpl implements ChatRoomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private Map<Long, ChatRoom> chatRooms = new HashMap<Long, ChatRoom>();

    public ChatRoomRepositoryImpl() {
    }

    public ChatRoom findById(long id) {
        return chatRooms.get(id);
    }

    public Collection<ChatRoom> findAll() {
        return chatRooms.values();
    }

    public void update(ChatRoom chatRoom) {
        chatRooms.put(chatRoom.getId(), chatRoom);
    }

    public void delete(long id) {
        chatRooms.remove(id);
    }
}
