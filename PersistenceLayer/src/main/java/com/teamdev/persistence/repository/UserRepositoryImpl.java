package com.teamdev.persistence.repository;

import com.teamdev.persistence.dom.User;

import java.util.*;

public class UserRepositoryImpl implements UserRepository {

    private Map<Long, User> users = new HashMap<Long, User>();

    public UserRepositoryImpl() {
    }

    public User findById(long id) {
        return users.get(id);
    }

    public User findByMail(String mail) {
        User user = users.values().stream().filter(x -> x.getMail().equals(mail)).findFirst().orElse(null);
        return user;
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
