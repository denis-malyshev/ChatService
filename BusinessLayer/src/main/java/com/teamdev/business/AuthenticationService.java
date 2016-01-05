package com.teamdev.business;

import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.impl.dto.Token;
import com.teamdev.business.impl.dto.UserEmail;
import com.teamdev.business.impl.dto.UserPassword;

public interface AuthenticationService {

    Token login(UserEmail email, UserPassword password) throws AuthenticationException;

    void validation(Token token);
}
