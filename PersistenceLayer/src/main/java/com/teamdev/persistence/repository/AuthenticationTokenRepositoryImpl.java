package com.teamdev.persistence.repository;

import com.teamdev.persistence.AuthenticationTokenRepository;
import com.teamdev.persistence.dom.AuthenticationToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AuthenticationTokenRepositoryImpl implements AuthenticationTokenRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private Map<Long, AuthenticationToken> tokens = new HashMap<>();

    public AuthenticationTokenRepositoryImpl() {
    }

    public AuthenticationToken findById(long id) {
        return tokens.get(id);
    }

    public Collection<AuthenticationToken> findAll() {
        return tokens.values();
    }

    @Override
    public int count() {
        return tokens.size();
    }

    public void update(AuthenticationToken token) {
        tokens.put(token.getId(), token);
    }

    public void delete(long id) {
        tokens.remove(id);
    }
}
