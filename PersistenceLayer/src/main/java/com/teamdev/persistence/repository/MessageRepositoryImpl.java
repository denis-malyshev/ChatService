package com.teamdev.persistence.repository;

import com.teamdev.persistence.MessageRepository;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.dom.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MessageRepositoryImpl implements MessageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private Map<Long, Message> messages = new HashMap<>();

    public Message findById(long id) {
        return messages.get(id);
    }

    public Collection<Message> findAll() {
        return messages.values();
    }

    public void update(Message message) {
        messages.put(message.getId(), message);
    }

    public Collection<Message> findBySender(User sender) {
        return Collections.emptySet();
    }

    public Collection<Message> findAllAfter(final LocalDateTime time) {
        return messages.values().stream().filter(x->x.getTime().equals(time)).collect(Collectors.toList());
    }

    public int count() {
        return messages.size();
    }

    public void delete(long id) {
        messages.remove(id);
    }

}
