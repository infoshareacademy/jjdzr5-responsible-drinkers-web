package com.infoshareacademy.responsibledrinkersweb.entity.control;

import com.infoshareacademy.drinkers.domain.drink.Alcoholic;
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

    public List<DrinkDAO> findByStatus(String sort, Status status) {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d WHERE d.status = :status ORDER BY d." + sort, DrinkDAO.class)
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

    public List<DrinkDAO> findByNameAndStatus(String sort, String searchString, Status accepted) {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d WHERE d.strDrink LIKE :searchString AND " +
                        "d.status = :status order by d." + sort, DrinkDAO.class)
                .setParameter("searchString", "%" + searchString + "%")
                .setParameter("status", accepted)
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

    public List<DrinkDAO> filteredList(String searchString, Alcoholic alcoholic, Status status, FilterElements filterElements) {
        String alcohol = "Alcoholic";
        if (Alcoholic.NON_ALCOHOLIC.equals(alcoholic)) {
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

    public List<DrinkDAO> searchDrinks(String keyword) {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d WHERE d.strDrink LIKE :keyword ", DrinkDAO.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }

    public List<DrinkDAO> getSearchAndFilterResult(String sort, String keyword, Alcoholic alcoholic, Status status) {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d WHERE d.strDrink LIKE :keyword AND " +
                        "d.strAlcoholic = :alcohol AND d.status = :status ORDER BY d." + sort, DrinkDAO.class)
                .setParameter("keyword", "%" + keyword + "%")
                .setParameter("alcohol", alcoholic.getName())
                .setParameter("status", status)
                .getResultList();
    }

    public List<DrinkDAO> getSearchAndFilterResult(String sort, String keyword, Alcoholic alcoholic, FilterElements filterElements, Status status) {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d WHERE d.strDrink LIKE :keyword AND " +
                        "d.strAlcoholic = :alcohol AND d.strIngredient LIKE :filterElements AND d.status = :status " +
                        "ORDER BY d." + sort, DrinkDAO.class)
                .setParameter("keyword", "%" + keyword + "%")
                .setParameter("alcohol", alcoholic.getName())
                .setParameter("filterElements", "%" + filterElements.getName() + "%")
                .setParameter("status", status)
                .getResultList();
    }

    public List<DrinkDAO> getAlcoholicResults(String sort, Alcoholic alcoholic, String keyword, Status status) {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d WHERE d.strAlcoholic = :alcoholic AND " +
                        "d.status = :status AND d.strDrink LIKE :keyword ORDER BY d." + sort, DrinkDAO.class)
                .setParameter("alcoholic", alcoholic.getName())
                .setParameter("keyword", "%" + keyword + "%")
                .setParameter("status", status)
                .getResultList();
    }

    public List<DrinkDAO> getFilterResults(String sort, FilterElements filterElements, String keyword, Status status) {
        return entityManager.createQuery("SELECT d FROM DrinkDAO d WHERE d.strIngredient LIKE :filterElements AND " +
                        "d.status = :status AND d.strDrink LIKE :keyword ORDER BY d." + sort, DrinkDAO.class)
                .setParameter("filterElements", "%" + filterElements.getName() + "%")
                .setParameter("status", status)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }
}
