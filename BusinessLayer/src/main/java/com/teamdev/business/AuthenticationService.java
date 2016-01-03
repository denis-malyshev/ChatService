package com.teamdev.business;

import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.tinytypes.Token;

public interface AuthenticationService {

    Token login(String email, String password) throws AuthenticationException;
}
