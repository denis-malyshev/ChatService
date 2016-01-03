package com.teamdev.business.impl;

import com.teamdev.business.UserService;
import com.teamdev.business.impl.dto.UserDTO;
import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.tinytypes.UserEmail;
import com.teamdev.business.tinytypes.UserId;
import com.teamdev.business.tinytypes.UserName;
import com.teamdev.business.tinytypes.UserPassword;
import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl() {
    }

    public UserDTO register(UserName name, UserEmail email, UserPassword password) throws AuthenticationException {

        if (userRepository.userCount() > 0 && userRepository.findByMail(email.getEmail()) != null) {
            throw new AuthenticationException("User with the same mail already exists.");
        }

        User user = new User(name.getName(), email.getEmail(), password.getPassword());
        userRepository.update(user);
        return new UserDTO(user.getId(), user.getFirstName(), user.getEmail());
    }

    @Override
    public UserDTO findById(UserId userId) {

        User user = userRepository.findById(userId.getId());

        if (user == null)
            return null;

        return new UserDTO(userId.getId(), user.getFirstName(), user.getEmail());
    }
}
