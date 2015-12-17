package com.teamdev.business;

import com.teamdev.persistence.dom.User;

public interface ChatRoomService<ChatRoom, AuthenticationToken> {

    boolean register(ChatRoom chatRoom);

    void joinToChatRoom(AuthenticationToken token, User user, long chatRoomId) throws Exception;

    void sendMessage(AuthenticationToken token, String text, User user, ChatRoom chatRoomId) throws Exception;

    void leaveChatRoom(AuthenticationToken token, User user, long chatRoomId) throws Exception;

}
