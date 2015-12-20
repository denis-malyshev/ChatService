package com.teamdev.business;

import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.dom.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService<AuthenticationToken> {

    @Transactional
    void register(User user) throws AuthenticationError;

    @Transactional
    void sendPrivateMessage(AuthenticationToken token, String text, long senderId, long receiverId) throws AuthenticationError;
}
