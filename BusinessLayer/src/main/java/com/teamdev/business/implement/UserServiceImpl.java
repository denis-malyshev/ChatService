package com.teamdev.business.implement;

import com.teamdev.business.UserService;
import com.teamdev.business.implement.error.AuthenticationError;
import com.teamdev.persistence.UserRepository;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.RepositoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    private long count = 0;

    public UserServiceImpl() {
    }

    public UserServiceImpl(RepositoryFactory repositoryFactory) {
        this.userRepository = repositoryFactory.getUserRepository();
    }

    public void register(User user) throws AuthenticationError {
        if (userRepository.count() > 0)
            if (userRepository.findByMail(user.getMail()) != null) {
                throw new AuthenticationError("User with the same mail already exists.");
            }
        user.setId(count++);
        userRepository.update(user);
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
