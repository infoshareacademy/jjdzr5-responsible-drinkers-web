package com.infoshareacademy.responsibledrinkersweb.entity.control;

import com.infoshareacademy.responsibledrinkersweb.entity.DrinkDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
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
    public List<DrinkDAO> findAll() {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d", DrinkDAO.class).getResultList();
    }

    @Override
    public void update(DrinkDAO drinkDAO) {
        entityManager.merge(drinkDAO);
    }

    @Override
    public void saveAll(List<DrinkDAO> drinkDAOs) {
        for (DrinkDAO drinkDAO : drinkDAOs) {
            save(drinkDAO);
        }
    }

    @Override
    public void delete(DrinkDAO drinkDAO) {
        entityManager.remove(drinkDAO);
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM DrinkDAO").executeUpdate();
    }

    @Override
    public void deleteByUUID(UUID uuid) {
        entityManager.createQuery("DELETE FROM DrinkDAO d WHERE d.id = :uuid")
                .setParameter("uuid", uuid)
                .executeUpdate();
    }
}
