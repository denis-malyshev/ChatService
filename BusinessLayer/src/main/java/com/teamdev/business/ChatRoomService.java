package com.teamdev.business;

import com.teamdev.business.implement.dto.ChatRoomDto;
import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.dom.ChatRoom;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface ChatRoomService {

    @Transactional
    void create(ChatRoom chatRoom);

    @Transactional
    void joinToChatRoom(String token, long user, long chatRoomId) throws AuthenticationError;

    @Transactional
    void leaveChatRoom(String token, long user, long chatRoomId) throws AuthenticationError;

    Collection<ChatRoomDto> findAll();

}
