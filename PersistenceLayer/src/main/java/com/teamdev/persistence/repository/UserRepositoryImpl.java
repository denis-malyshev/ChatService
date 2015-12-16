package com.teamdev.persistence.repository;

import com.teamdev.persistence.dom.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {

    private Map<Long, User> users = new HashMap<Long, User>();

    public UserRepositoryImpl() {
    }

    public User findById(long id) {
        return users.get(id);
    }

    public Collection<User> findAll() {
        return users.values();
    }

    public void update(User user) {
        users.put(user.getId(), user);
    }

    public void delete(long id) {
        users.remove(id);
    }

    public Map<Long, User> getUsers() {
        return users;
    }
}
