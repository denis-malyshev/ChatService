package com.teamdev.business.implement;

import com.teamdev.business.MessageService;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.MessageRepository;
import org.springframework.stereotype.Service;

@Service("messageService")
public class MessageServiceImpl implements MessageService<Message> {

    private MessageRepository repository;
    private long count = 0;

    public MessageServiceImpl() {
    }

    public MessageServiceImpl(MessageRepository repository) {
        this.repository = repository;
    }

    public void register(Message message) {
        message.setId(count++);
        repository.update(message);
    }
}
