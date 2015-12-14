package com.teamdev.business;

public interface ChatRoomService<ChatRoom, AuthenticationToken> {

    boolean register(ChatRoom chatRoom);

    void joinToChatRoom(AuthenticationToken token, long userId, long chatRoomId);

    void sendMessage(AuthenticationToken token, String text, long userId, long chatRoomId);

    void leaveChatRoom(AuthenticationToken token, long userId, long chatRoomId);

}
