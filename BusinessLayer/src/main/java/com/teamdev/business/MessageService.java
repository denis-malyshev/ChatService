package com.teamdev.business;

import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.persistence.dom.Message;

public interface MessageService {

    void sendMessage(String token, String text, long user, long chatRoomId) throws AuthenticationException;

    void sendPrivateMessage(String token, String text, long senderId, long receiverId) throws AuthenticationException;
}
