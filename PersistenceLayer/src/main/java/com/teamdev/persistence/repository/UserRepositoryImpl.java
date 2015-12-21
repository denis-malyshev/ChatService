package com.teamdev.persistence.repository;

import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private Map<Long, User> users = new HashMap<>();

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
        //entityManager.createQuery("from User", User.class).getResultList();
        return users.values();
    }

    public int count() {
        return users.size();
    }

    public void update(User user) {
        users.put(user.getId(), user);
        //entityManager.persist(user);
    }

    public void delete(long id) {
        users.remove(id);
    }
}
