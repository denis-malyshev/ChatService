package com.teamdev.persistence.repository;

import com.teamdev.persistence.AuthenticationTokenRepository;
import com.teamdev.persistence.dom.AuthenticationToken;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AuthenticationTokenRepositoryImpl implements AuthenticationTokenRepository {

    private Map<Long, AuthenticationToken> tokens = new HashMap<>();
    private long id = 1;

    public AuthenticationTokenRepositoryImpl() {
    }

    public AuthenticationToken findById(long id) {
        return tokens.get(id);
    }

    @Override
    public AuthenticationToken findByKey(String key) {
        return tokens.values().stream().filter(x -> x.getKey().equals(key)).findFirst().orElse(null);
    }

    public Collection<AuthenticationToken> findAll() {
        return tokens.values();
    }

    public void update(AuthenticationToken token) {
        if (token.getId() == 0) {
            token.setId(id++);
        }
        tokens.put(token.getId(), token);
    }

    public void delete(long id) {
        tokens.remove(id);
    }
}
