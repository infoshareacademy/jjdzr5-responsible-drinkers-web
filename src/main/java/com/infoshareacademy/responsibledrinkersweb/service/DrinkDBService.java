package com.infoshareacademy.responsibledrinkersweb.service;

import com.infoshareacademy.drinkers.domain.drink.Alcoholic;
import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.domain.drink.Status;
import com.infoshareacademy.drinkers.service.filtering.FilterElements;
import com.infoshareacademy.responsibledrinkersweb.entity.DrinkDAO;
import com.infoshareacademy.responsibledrinkersweb.entity.control.DBDrinkDAOManager;
import com.infoshareacademy.responsibledrinkersweb.mapper.DrinkMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public List<Drink> getAcceptedDrinks(String sort) {
        List<DrinkDAO> accepted = dbDrinkDAOManager.findByStatus(sort, Status.ACCEPTED);
        return drinkMapper.mapToDrinkList(accepted);
    }

    public void addDrink(Drink drink) {
        dbDrinkDAOManager.save(drinkMapper.mapDinkToDrinkDAO(drink));
    }

    public Drink getDrinkById(int id) {
        DrinkDAO drinkDAO = dbDrinkDAOManager.findById(id);
        return drinkMapper.mapDrinkDAOToDrink(drinkDAO);
    }

    public void deleteDrink(UUID id) {
        dbDrinkDAOManager.delete(id);
    }

    public List<Drink> getSearchResults(String sort, String searchString, Status accepted) {
        List<DrinkDAO> drinks = dbDrinkDAOManager.findByNameAndStatus(sort, searchString, accepted);
        return drinkMapper.mapToDrinkList(drinks);
    }

    public void updateDrink(Drink drink) {
        dbDrinkDAOManager.update(drinkMapper.mapDinkToDrinkDAO(drink));
    }

    public List<Drink> filter(Boolean isAlcoholic) {
        List<DrinkDAO> drinks = dbDrinkDAOManager.findByAlcoholic(isAlcoholic);
        return drinkMapper.mapToDrinkList(drinks);
    }

    public Drink getDrinkByUUID(UUID uuid) {
        return drinkMapper.mapDrinkDAOToDrink(dbDrinkDAOManager.find(uuid));
    }


    public List<Drink> getAllDrinksByStatus(String sort, Status status) {
        return drinkMapper.mapToDrinkList(dbDrinkDAOManager.findByStatus(sort, status));
    }

    public List<Drink> getSearchAndFilterResult(String sort, String keyword, Alcoholic alcoholic, Status status) {
        return drinkMapper.mapToDrinkList(dbDrinkDAOManager.getSearchAndFilterResult(sort, keyword, alcoholic, status));
    }

    public List<Drink> getSearchAndFilterResult(String sort, String keyword, Alcoholic alcoholic, FilterElements filterElements, Status status) {
        return drinkMapper.mapToDrinkList(dbDrinkDAOManager.getSearchAndFilterResult(sort, keyword, alcoholic, filterElements, status));
    }

    public List<Drink> getAlcoholicResults(String sort, Alcoholic alcoholic, String keyword, Status status) {
        return drinkMapper.mapToDrinkList(dbDrinkDAOManager.getAlcoholicResults(sort, alcoholic, keyword, status));
    }

    public List<Drink> getFilterResults(String sort, FilterElements filterElements, String keyword, Status status) {
        return drinkMapper.mapToDrinkList(dbDrinkDAOManager.getFilterResults(sort, filterElements, keyword, status));
    }

    public List<Drink> getAllDrinks(String sort) {
        return drinkMapper.mapToDrinkList(dbDrinkDAOManager.findAll(sort));
    }


    public List<Drink> getSearchAndFilterResult(String sort, String keyword, Alcoholic alcoholic) {
        return drinkMapper.mapToDrinkList(dbDrinkDAOManager.getSearchAndFilterResult(sort, keyword, alcoholic));
    }

    public List<Drink> getSearchAndFilterResult(String sort, String keyword, Alcoholic alcoholic, FilterElements filterElements) {
        return drinkMapper.mapToDrinkList(dbDrinkDAOManager.getSearchAndFilterResult(sort, keyword, alcoholic, filterElements));
    }

    public List<Drink> getSearchAndFilterResult(String sort, String keyword, FilterElements filterElements, Status status) {
        return drinkMapper.mapToDrinkList(dbDrinkDAOManager.getSearchAndFilterResult(sort, keyword, filterElements, status));
    }

    public List<Drink> getSearchAndFilterResult(String sort, String keyword, FilterElements filterElements) {
        return drinkMapper.mapToDrinkList(dbDrinkDAOManager.getSearchAndFilterResult(sort, keyword, filterElements));
    }

    public List<Drink> getSearchAndFilterResult(String sort, String keyword, Status status) {
        return drinkMapper.mapToDrinkList(dbDrinkDAOManager.getSearchAndFilterResult(sort, keyword, status));
    }

    public List<Drink> getSearchAndFilterResult(String sort, String keyword) {
        return drinkMapper.mapToDrinkList(dbDrinkDAOManager.getSearchAndFilterResult(sort, keyword));
    }

    public List<Drink> getUserDrinks(UUID id) {
        // TODO: change to Query
        List<DrinkDAO> userDrinks = dbDrinkDAOManager.findAll()
                .stream()
                .filter(drinkDAO -> drinkDAO.getUserDAO().getId().equals(id))
                .toList();
        return drinkMapper.mapToDrinkList(userDrinks);
    }
}
