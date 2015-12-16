package com.teamdev.business;

import com.teamdev.persistence.exception.EntityNotFoundException;

public interface AuthenticationService<Token> {

    Token login(String mail, String password) throws Exception;

    boolean isValid(Token token) throws  Exception;
}
