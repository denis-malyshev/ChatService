package com.teamdev.persistence;

import com.teamdev.persistence.dom.User;

public interface UserRepository extends Repository<User> {

    User findByMail(String mail);

    int count();
}
