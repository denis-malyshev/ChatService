package com.teamdev.business;

import com.teamdev.business.impl.dto.ChatRoomDTO;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.persistence.dom.ChatRoom;

import java.util.Collection;

public interface ChatRoomService {

    ChatRoomDTO create(ChatRoom chatRoom);

    void joinToChatRoom(String token, long user, long chatRoomId) throws AuthenticationException;

    void leaveChatRoom(String token, long user, long chatRoomId) throws AuthenticationException;

    Collection<ChatRoomDTO> findAll();

}
