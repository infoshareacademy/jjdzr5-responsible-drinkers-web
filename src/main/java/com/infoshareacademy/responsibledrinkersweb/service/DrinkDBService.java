package com.infoshareacademy.responsibledrinkersweb.service;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.domain.drink.Status;
import com.infoshareacademy.responsibledrinkersweb.entity.DrinkDAO;
import com.infoshareacademy.responsibledrinkersweb.entity.control.DBDrinkDAOManager;
import com.infoshareacademy.responsibledrinkersweb.mapper.DrinkMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrinkDBService {
    private final DBDrinkDAOManager dbDrinkDAOManager;
    private final DrinkMapper drinkMapper;

    public DrinkDBService(DBDrinkDAOManager dbDrinkDAOManager, DrinkMapper drinkMapper) {
        this.dbDrinkDAOManager = dbDrinkDAOManager;
        this.drinkMapper = drinkMapper;
    }

    public List<Drink> getDrinks() {
        List<DrinkDAO> drinkDAOList = dbDrinkDAOManager.findAll();
        return drinkMapper.mapToDrinkList(drinkDAOList);
    }

    public List<Drink> getNewest(int count) {
        List<DrinkDAO> newest = dbDrinkDAOManager.findNewest(count);
        return drinkMapper.mapToDrinkList(newest);
    }

    public List<Drink> getNewestAccepted(int count) {
        List<DrinkDAO> newest = dbDrinkDAOManager.findNewestAccepted(count);
        return drinkMapper.mapToDrinkList(newest);
    }

    public List<Drink> getAcceptedDrinks() {
        List<DrinkDAO> accepted = dbDrinkDAOManager.findByStatus(Status.ACCEPTED);
        return drinkMapper.mapToDrinkList(accepted);
    }

    public void addDrink(Drink drink) {
        dbDrinkDAOManager.save(drinkMapper.mapDinkToDrinkDAO(drink));
    }

    public Drink getDrinkById(int id) {
        DrinkDAO drinkDAO = dbDrinkDAOManager.findById(id);
        return drinkMapper.mapDrinkDAOToDrink(drinkDAO);
    }

    public void deleteDrink(int id) {
        dbDrinkDAOManager.delete(id);
    }

    public List<Drink> getSearchResults(String searchString) {
        List<DrinkDAO> drinks = dbDrinkDAOManager.findByName(searchString);
        return drinkMapper.mapToDrinkList(drinks);
    }

    public void updateDrink(Drink drink) {
        dbDrinkDAOManager.update(drinkMapper.mapDinkToDrinkDAO(drink));
    }

    public List<Drink> filter(Boolean isAlcoholic) {
        List<DrinkDAO> drinks = dbDrinkDAOManager.findByAlcoholic(isAlcoholic);
        return drinkMapper.mapToDrinkList(drinks);
    }
}
