package com.teamdev.business.implement;

import com.teamdev.business.MessageService;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.repository.MessageRepositoryImpl;

public class MessageServiceImpl implements MessageService<Message> {

    private MessageRepositoryImpl repository = new MessageRepositoryImpl();
    private long count = 0;

    public void register(Message message) {
        message.setId(count++);
        repository.update(message);
    }
}
