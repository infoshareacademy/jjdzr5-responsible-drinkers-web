package com.infoshareacademy.responsibledrinkersweb.entity.control;

import com.infoshareacademy.responsibledrinkersweb.entity.DrinkDAO;

import java.util.List;
import java.util.UUID;

public interface DrinkDAOManager {

    void save(DrinkDAO drinkDAO);

    DrinkDAO find(UUID uuid);

    List<DrinkDAO> findAll();

    void update(DrinkDAO drinkDAO);

}
