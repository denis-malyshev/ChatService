package com.teamdev.business;

public interface AuthenticationService<Token> {

    boolean isValid(Token token);
}
