package com.teamdev.persistence.repository;

import com.teamdev.persistence.dom.AuthenticationToken;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationTokenRepositoryImpl implements AuthenticationTokenRepository {

    private Map<Long, AuthenticationToken> tokens = new HashMap<Long, AuthenticationToken>();

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
