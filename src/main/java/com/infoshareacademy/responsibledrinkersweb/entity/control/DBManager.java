package com.infoshareacademy.responsibledrinkersweb.entity.control;

import java.util.List;

public interface DBManager<T> {

        public <T> T find(Class<T> clazz,Long id);

        public <T> void save(T entity);

        public <T> void delete(T entity);

        public <T> T update(T entity);

        public <T> List<T> findAll(Class<T> clazz);

        public <T> void saveAll(List<T> entities);
}
