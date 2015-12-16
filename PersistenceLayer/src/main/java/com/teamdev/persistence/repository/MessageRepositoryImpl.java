package com.teamdev.persistence.repository;

import com.teamdev.persistence.dom.Message;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MessageRepositoryImpl implements MessageRepository {

    private Map<Long, Message> messages = new HashMap<Long, Message>();

    public Message findById(long id) {
        return messages.get(id);
    }

    public Collection<Message> findAll() {
        return messages.values();
    }

    public void update(Message message) {
        messages.put(message.getId(), message);
    }

    public void delete(long id) {
        messages.remove(id);
    }

}
