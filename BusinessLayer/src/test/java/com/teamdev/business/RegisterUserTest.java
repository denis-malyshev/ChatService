package com.teamdev.business;

import com.teamdev.business.implement.UserServiceImpl;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.repository.MessageRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegisterUserTest {

    private UserServiceImpl userService;

    @Before
    public void setUp() throws Exception {
        userService = new UserServiceImpl(new MessageRepository());
    }

    @Test
    public void registerFirstUser() throws Exception {
        final User user = new User("Vasya", "234", "vasya@gmail.com");
        userService.register(user);
        final int actual = userService.getRepository().getUsers().size();
        assertEquals("The number of users should be 1",1, actual);
    }
}
