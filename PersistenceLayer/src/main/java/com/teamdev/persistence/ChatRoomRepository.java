package com.teamdev.persistence;

import com.teamdev.persistence.dom.ChatRoom;

public interface ChatRoomRepository extends Repository<ChatRoom> {

    ChatRoom findByName(String name);
}
