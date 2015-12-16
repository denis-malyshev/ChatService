package com.teamdev.persistence;

import com.teamdev.persistence.exception.EntityNotFoundException;

import java.util.Collection;

public interface Repository<Entity> {

    Entity findById(long id) throws EntityNotFoundException;
    Collection<Entity> findAll();

    void update(Entity entity);
    void delete(long id) throws EntityNotFoundException;
}
