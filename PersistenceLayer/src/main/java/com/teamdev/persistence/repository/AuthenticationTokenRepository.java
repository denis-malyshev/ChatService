package com.teamdev.persistence.repository;

import com.teamdev.persistence.Repository;
import com.teamdev.persistence.dom.AuthenticationToken;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationTokenRepository implements Repository<AuthenticationToken> {

    private Map<Long, AuthenticationToken> tokens = new HashMap<Long, AuthenticationToken>();

    public AuthenticationTokenRepository() {
    }

    public AuthenticationToken findById(long id) {
        return tokens.get(id);
    }

    public Collection<AuthenticationToken> findAll() {
        return tokens.values();
    }

    public void update(AuthenticationToken token) {
        tokens.put(token.getId(), token);
    }

    public void delete(long id) {
        tokens.remove(id);
    }
}
