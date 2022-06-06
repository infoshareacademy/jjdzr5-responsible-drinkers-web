package com.infoshareacademy.responsibledrinkersweb.entity.control;

import com.infoshareacademy.responsibledrinkersweb.entity.DrinkDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Transactional
@Repository
public class DBDrinkDAOManager implements DrinkDAOManager {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(DrinkDAO drinkDAO) {
        entityManager.persist(drinkDAO);
    }

    @Override
    public DrinkDAO find(UUID uuid) {
        return entityManager.find(DrinkDAO.class, uuid);
    }

    @Override
    public DrinkDAO update(DrinkDAO drinkDAO) {
        return entityManager.merge(drinkDAO);
    }

    @Override
    public void delete(UUID uuid) {
        entityManager.remove(find(uuid));
    }
}
