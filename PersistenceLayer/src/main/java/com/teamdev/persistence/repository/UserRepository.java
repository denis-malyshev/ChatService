package com.teamdev.persistence.repository;

import com.teamdev.persistence.Repository;
import com.teamdev.persistence.dom.User;
import com.teamdev.persistence.exception.EntityNotFoundException;

public interface UserRepository extends Repository<User> {

    User findByMail(String mail);

    int count();
}
