package com.teamdev.business;

import org.springframework.transaction.annotation.Transactional;

public interface MessageService<Message> {

    @Transactional
    void register(Message message);
}
