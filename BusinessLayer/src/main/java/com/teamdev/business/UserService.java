package com.teamdev.business;

import com.teamdev.business.implement.dto.UserDto;
import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.dom.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    @Transactional
    void register(User user) throws AuthenticationError;

    UserDto findById(long userId);

    int count();
}
