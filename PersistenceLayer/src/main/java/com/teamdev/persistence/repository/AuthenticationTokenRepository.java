package com.teamdev.persistence.repository;

import com.teamdev.persistence.Repository;
import com.teamdev.persistence.dom.AuthenticationToken;

public interface AuthenticationTokenRepository extends Repository<AuthenticationToken> {

    int count();
}
