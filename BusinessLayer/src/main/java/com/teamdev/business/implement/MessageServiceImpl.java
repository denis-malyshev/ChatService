package com.teamdev.business.implement;

import com.teamdev.business.MessageService;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.repository.MessageRepository;
import com.teamdev.persistence.repository.MessageRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService<Message> {

    @Autowired
    private MessageRepository repository;
    private long count = 0;

    public MessageServiceImpl(MessageRepository repository) {
        this.repository = repository;
    }

    public void register(Message message) {
        message.setId(count++);
        repository.update(message);
    }
}
