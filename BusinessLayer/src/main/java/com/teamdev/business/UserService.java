package com.teamdev.business;

import com.teamdev.business.impl.dto.UserDTO;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.persistence.dom.User;

public interface UserService {

    UserDTO register(User user) throws AuthenticationException;

    UserDTO findById(long userId);
}
