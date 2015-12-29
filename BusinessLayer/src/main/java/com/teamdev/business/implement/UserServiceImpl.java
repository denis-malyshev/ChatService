package com.teamdev.business.implement;

import com.teamdev.business.UserService;
import com.teamdev.business.implement.dto.UserDto;
import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.RepositoryFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;
    private long count = 0;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(User user) throws AuthenticationError {
        if (userRepository.count() > 0)
            if (userRepository.findByMail(user.getMail()) != null) {
                throw new AuthenticationError("User with the same mail already exists.");
            }
        user.setId(count++);
        userRepository.update(user);
    }

    @Override
    public UserDto findById(long userId) {
        User user = userRepository.findById(userId);
        if (user == null)
            return null;
        UserDto userDto = new UserDto(userId, user.getFirstName(), user.getMail());
        return userDto;
    }

    @Override
    public int count() {
        return userRepository.count();
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
