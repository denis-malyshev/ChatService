package com.teamdev.business;

import com.teamdev.business.implement.error.AuthenticationError;
import org.springframework.stereotype.Service;

public interface AuthenticationService {

    void login(String mail, String password) throws AuthenticationError;

    boolean isValid(String token) throws AuthenticationError;
}
