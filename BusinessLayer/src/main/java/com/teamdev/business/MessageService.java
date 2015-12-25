package com.teamdev.business;

import com.teamdev.business.implement.error.AuthenticationError;
import org.springframework.transaction.annotation.Transactional;

public interface MessageService<Message> {

    @Transactional
    void create(Message message);

    @Transactional
    void sendMessage(String token, String text, long user, long chatRoomId) throws AuthenticationError;

    @Transactional
    void sendPrivateMessage(String token, String text, long senderId, long receiverId) throws AuthenticationError;
}
