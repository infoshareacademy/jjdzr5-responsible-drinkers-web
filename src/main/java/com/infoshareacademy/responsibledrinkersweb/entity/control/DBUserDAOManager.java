package com.infoshareacademy.responsibledrinkersweb.entity.control;

import com.infoshareacademy.responsibledrinkersweb.entity.UserDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Repository
@Transactional
public class DBUserDAOManager implements UserDAOManager {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(UserDAO user) {
        entityManager.persist(user);
    }

    @Override
    public UserDAO find(UUID id) {
        return entityManager.find(UserDAO.class, id);
    }

}
