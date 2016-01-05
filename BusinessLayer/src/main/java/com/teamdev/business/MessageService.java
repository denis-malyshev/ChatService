package com.teamdev.business;

import com.teamdev.business.impl.dto.MessageDTO;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.impl.exception.ChatRoomNotFoundException;
import com.teamdev.business.impl.exception.UserNotFoundException;
import com.teamdev.business.impl.dto.ChatRoomId;
import com.teamdev.business.impl.dto.Token;
import com.teamdev.business.impl.dto.UserId;

public interface MessageService {

    MessageDTO sendMessage(Token token, UserId userId, ChatRoomId chatRoomId, String text)
            throws AuthenticationException, UserNotFoundException, ChatRoomNotFoundException;

    MessageDTO sendPrivateMessage(Token token, UserId senderId, UserId receiverId, String text)
            throws AuthenticationException, UserNotFoundException;
}
