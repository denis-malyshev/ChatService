package com.teamdev.business;

import com.teamdev.business.impl.exception.AuthenticationException;

public interface AuthenticationService {

    String login(String mail, String password) throws AuthenticationException;

    void validation(String token) throws AuthenticationException;
}
