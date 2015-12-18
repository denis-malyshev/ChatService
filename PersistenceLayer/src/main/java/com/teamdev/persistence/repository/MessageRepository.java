package com.teamdev.persistence.repository;

import com.teamdev.persistence.Repository;
import com.teamdev.persistence.dom.Message;
import com.teamdev.persistence.dom.User;

import java.time.LocalDateTime;
import java.util.Collection;

public interface MessageRepository extends Repository<Message> {

    Collection<Message> findBySender(User sender);

    Collection<Message> findAllAfter(LocalDateTime time);

    int count();
}
