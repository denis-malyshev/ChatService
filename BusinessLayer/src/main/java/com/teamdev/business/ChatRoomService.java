package com.teamdev.business;

import com.teamdev.business.impl.dto.ChatRoomDTO;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.impl.exception.ChatRoomAlreadyExistsException;
import com.teamdev.business.impl.exception.ChatRoomNotFoundException;
import com.teamdev.business.impl.exception.UserNotFoundException;
import com.teamdev.business.tinytypes.ChatRoomId;
import com.teamdev.business.tinytypes.Token;
import com.teamdev.business.tinytypes.UserId;
import com.teamdev.persistence.dom.ChatRoom;

import java.util.Collection;

public interface ChatRoomService {

    ChatRoomDTO create(String chatRoomName) throws ChatRoomAlreadyExistsException;

    void joinToChatRoom(Token token, UserId userId, ChatRoomId chatRoomId)
            throws AuthenticationException, UserNotFoundException, ChatRoomNotFoundException;

    void leaveChatRoom(Token token, UserId userId, ChatRoomId chatRoomId)
            throws AuthenticationException, ChatRoomNotFoundException;

    Collection<ChatRoomDTO> findAll();

}
