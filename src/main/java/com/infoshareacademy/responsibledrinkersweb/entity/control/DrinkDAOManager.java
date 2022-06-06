package com.infoshareacademy.responsibledrinkersweb.entity.control;

import com.infoshareacademy.responsibledrinkersweb.entity.DrinkDAO;

import java.util.UUID;

public interface DrinkDAOManager {

    void save(DrinkDAO drinkDAO);

    DrinkDAO find(UUID uuid);

    DrinkDAO update(DrinkDAO drinkDAO);

    void delete(UUID uuid);
}
