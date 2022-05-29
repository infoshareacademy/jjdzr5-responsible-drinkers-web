package com.infoshareacademy.responsibledrinkersweb.entity.control;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class DBEntityManager<T> implements DBManager<T> {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public <T1> T1 find(Class<T1> clazz, Long id) {
        return entityManager.find(clazz, id);
    }


    @Override
    public <T1> void save(T1 entity) {
        entityManager.persist(entity);
    }

    @Override
    public <T1> void delete(T1 entity) {
        entityManager.remove(entity);
    }

    @Override
    public <T1> T1 update(T1 entity) {
        return entityManager.merge(entity);
    }

    @Override
    public <T1> List<T1> findAll(Class<T1> clazz) {
        return entityManager.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e", clazz).getResultList();
    }

    @Override
    public <T1> void saveAll(List<T1> entities) {
        for (T1 entity : entities) {
            save(entity);
        }
    }
}
