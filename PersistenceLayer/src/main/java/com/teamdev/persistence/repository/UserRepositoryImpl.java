package com.teamdev.persistence.repository;

import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private Map<Long, User> users = new HashMap<>();
    private AtomicLong id = new AtomicLong(1);

    public UserRepositoryImpl() {
    }

    public User findById(long id) {
        return users.get(id);
    }

    public User findByMail(String mail) {
        User user = users.values().stream().filter(x -> x.getEmail().equals(mail)).findFirst().orElse(null);
        return user;
    }

    public Collection<User> findAll() {
        return users.values();
    }

    public int userCount() {
        return users.size();
    }

    public void update(User user) {
        if (user.getId() == 0) {
            user.setId(id.getAndIncrement());
        }
        users.put(user.getId(), user);
    }

    public void delete(long id) {
        users.remove(id);
    }
}
