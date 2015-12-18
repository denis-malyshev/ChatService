package com.teamdev.business;

import com.teamdev.business.implement.error.AuthenticationError;

public interface AuthenticationService<Token> {

    void login(String mail, String password) throws AuthenticationError;

    boolean isValid(Token token) throws AuthenticationError;
}
