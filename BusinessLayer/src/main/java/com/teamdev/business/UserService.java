package com.teamdev.business;

import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.dom.User;

public interface UserService<AuthenticationToken> {

    void register(User user) throws AuthenticationError;

    void sendPrivateMessage(AuthenticationToken token, String text, long senderId, long receiverId) throws AuthenticationError;
}
