package com.teamdev.persistence.repository;

import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.exception.EntityNotFoundException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {

    private Map<Long, User> users = new HashMap<Long, User>();

    public UserRepositoryImpl() {
    }

    public User findById(long id) {
        return users.get(id);
    }

    public User findByMail(String mail) {
        Iterator<User> iterator = getUsers().iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getMail().equals(mail))
                return user;
        }
        return null;
    }

    public Collection<User> findAll() {
        return users.values();
    }

    public int count() {
        return users.size();
    }

    public void update(User user) {
        users.put(user.getId(), user);
    }

    public void delete(long id) {
        users.remove(id);
    }

    public Collection<User> getUsers() {
        return users.values();
    }
}
