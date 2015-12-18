package com.teamdev.business;

import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.dom.ChatRoom;

public interface ChatRoomService<AuthenticationToken> {

    boolean create(ChatRoom chatRoom);

    void joinToChatRoom(AuthenticationToken token, long user, long chatRoomId) throws AuthenticationError;

    void sendMessage(AuthenticationToken token, String text, long user, long chatRoomId) throws AuthenticationError;

    void leaveChatRoom(AuthenticationToken token, long user, long chatRoomId) throws AuthenticationError;

}
