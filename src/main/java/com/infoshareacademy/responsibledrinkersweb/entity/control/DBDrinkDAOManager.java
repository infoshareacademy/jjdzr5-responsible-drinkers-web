package com.infoshareacademy.responsibledrinkersweb.entity.control;

import com.infoshareacademy.drinkers.domain.drink.Status;
import com.infoshareacademy.drinkers.service.filtering.FilterElements;
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

    public List<DrinkDAO> findNewest(int count) {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d ORDER BY d.dateModified DESC", DrinkDAO.class)
                .setMaxResults(count)
                .getResultList();
    }

    public List<DrinkDAO> findNewestAccepted(int count) {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d WHERE d.status = 'ACCEPTED' ORDER BY d.dateModified DESC", DrinkDAO.class)
                .setMaxResults(count)
                .getResultList();

    }

    public List<DrinkDAO> findByStatus(Status status) {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d WHERE d.status = :status", DrinkDAO.class)
                .setParameter("status", status)
                .getResultList();
    }

    public DrinkDAO findById(int id) {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d WHERE d.idDrink = :id", DrinkDAO.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public void delete(UUID id) {
        DrinkDAO byId = find(id);
        entityManager.remove(byId);
    }

    public List<DrinkDAO> findByName(String searchString) {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d WHERE d.strDrink LIKE :searchString", DrinkDAO.class)
                .setParameter("searchString", "%" + searchString + "%")
                .getResultList();
    }

    public List<DrinkDAO> findByAlcoholic(Boolean isAlcoholic) {
        if (Boolean.TRUE.equals(isAlcoholic)) {
            return entityManager.createQuery("SELECT d FROM DrinkDAO d WHERE d.strAlcoholic = 'Alcoholic'", DrinkDAO.class)
                    .getResultList();
        } else {
            return entityManager.createQuery("SELECT d FROM DrinkDAO d WHERE d.strAlcoholic = 'Non alcoholic'", DrinkDAO.class)
                    .getResultList();
        }
    }

    public List<DrinkDAO> sortByName() {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d ORDER BY d.strDrink", DrinkDAO.class)
                .getResultList();
    }

    public List<DrinkDAO> sortByAlcoholic() {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d ORDER BY d.strAlcoholic", DrinkDAO.class)
                .getResultList();
    }

    public List<DrinkDAO> sortByDate() {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d ORDER BY d.dateModified", DrinkDAO.class)
                .getResultList();
    }

    public List<DrinkDAO> sortByStatus() {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d ORDER BY d.status", DrinkDAO.class)
                .getResultList();
    }

    public List<DrinkDAO> filteredList(String searchString, Boolean isAlcoholic, Status status, FilterElements filterElements) {
        String alcohol = "Alcoholic";
        if (!isAlcoholic) {
            alcohol = "Non alcoholic";
        }

        return entityManager.createQuery("SELECT d FROM DrinkDAO d WHERE d.strDrink LIKE :searchString AND " +
                        "d.strAlcoholic LIKE :alcohol AND d.status = :status AND d.strIngredient LIKE :filterElements  ", DrinkDAO.class)
                .setParameter("searchString", "%" + searchString + "%")
                .setParameter("alcohol", alcohol)
                .setParameter("status", status)
                .setParameter("filterElements", "%" + filterElements.getName() + "%")
                .getResultList();
    }
}
