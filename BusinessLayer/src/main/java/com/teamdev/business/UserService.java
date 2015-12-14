package com.teamdev.business;

public interface UserService<User, AuthenticationToken> {

    boolean register(User user);

    AuthenticationToken login(String userMail, long userId);

    void sendPrivateMessage(AuthenticationToken token, String text, long senderId, long receiverId);
}
