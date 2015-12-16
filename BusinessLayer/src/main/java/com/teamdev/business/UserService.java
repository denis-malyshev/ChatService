package com.teamdev.business;

public interface UserService<User, AuthenticationToken> {

    void register(User user);

    void sendPrivateMessage(AuthenticationToken token, String text, User sender, User receiver) throws Exception;
}
