package com.teamdev.business;

public interface AuthenticationService<Token> {

    void login(String mail, String password) throws Exception;

    boolean isValid(Token token) throws  Exception;
}
