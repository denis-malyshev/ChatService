package com.teamdev.persistence;

import com.teamdev.persistence.dom.AuthenticationToken;

public interface AuthenticationTokenRepository extends Repository<AuthenticationToken> {

    AuthenticationToken findByKey(String key);
}
