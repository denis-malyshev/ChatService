package com.teamdev.business;

import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.tinytypes.Token;
import com.teamdev.business.tinytypes.UserEmail;
import com.teamdev.business.tinytypes.UserPassword;

public interface AuthenticationService {

    Token login(UserEmail email, UserPassword password) throws AuthenticationException;
}
