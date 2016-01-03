package com.teamdev.business;

import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.tinytypes.ChatRoomId;
import com.teamdev.business.tinytypes.Token;
import com.teamdev.business.tinytypes.UserId;

public interface MessageService {

    void sendMessage(Token token, UserId userId, ChatRoomId chatRoomId, String text) throws AuthenticationException;

    void sendPrivateMessage(Token token, UserId senderId, UserId receiverId, String text) throws AuthenticationException;
}
