package com.teamdev.business;

import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.dom.ChatRoom;
import org.springframework.transaction.annotation.Transactional;

public interface ChatRoomService<AuthenticationToken> {

    @Transactional
    boolean create(ChatRoom chatRoom);

    @Transactional
    void joinToChatRoom(AuthenticationToken token, long user, long chatRoomId) throws AuthenticationError;

    @Transactional
    void sendMessage(AuthenticationToken token, String text, long user, long chatRoomId) throws AuthenticationError;

    @Transactional
    void leaveChatRoom(AuthenticationToken token, long user, long chatRoomId) throws AuthenticationError;

}
