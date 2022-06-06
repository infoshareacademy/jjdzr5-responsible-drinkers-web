package com.infoshareacademy.responsibledrinkersweb.entity.control;

import com.infoshareacademy.responsibledrinkersweb.entity.UserDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DBUserDAOManager implements UserDAOManager {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void save(UserDAO user) {
        entityManager.persist(user);
    }

    @Override
    public UserDAO find(Long id) {
        return entityManager.find(UserDAO.class, id);
    }

    @Override
    public UserDAO update(UserDAO user) {
        return entityManager.merge(user);
    }

    @Override
    public void delete(UserDAO user) {
        entityManager.remove(user);
    }
}
