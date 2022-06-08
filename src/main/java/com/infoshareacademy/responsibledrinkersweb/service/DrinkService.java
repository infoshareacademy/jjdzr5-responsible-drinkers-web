package com.infoshareacademy.responsibledrinkersweb.service;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.domain.drink.Status;
import com.infoshareacademy.responsibledrinkersweb.entity.DrinkDAO;
import com.infoshareacademy.responsibledrinkersweb.entity.control.DBDrinkDAOManager;
import com.infoshareacademy.responsibledrinkersweb.exceptions.ImageNotFound;
import com.infoshareacademy.responsibledrinkersweb.mapper.DrinkMapper;
import com.infoshareacademy.responsibledrinkersweb.repository.DrinkRepository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DrinkService {

    private final List<Drink> drinkList = new DrinkRepository().getRepository();
    private final DBDrinkDAOManager dbDrinkDAOManager;
    private final DrinkMapper drinkMapper;
    private final DrinkDBService drinkDBService;

    public DrinkService(DBDrinkDAOManager dbDrinkDAOManager, DrinkMapper drinkMapper, DrinkDBService drinkDBService) {
        this.dbDrinkDAOManager = dbDrinkDAOManager;
        this.drinkMapper = drinkMapper;
        this.drinkDBService = drinkDBService;
    }

    public List<Drink> getDrinks() {
        List<DrinkDAO> drinkDAOList = dbDrinkDAOManager.findAll();
        return drinkMapper.mapToDrinkList(drinkDAOList);
    }

    public List<Drink> getNewestAccepted(int count) {
        return drinkDBService.getNewestAccepted(count);
    }

    public List<Drink> getAcceptedDrinks() {
        return drinkDBService.getAcceptedDrinks();
    }

    public void addDrink(Drink drink) {
        String s = ImageNotFound.verifyURL(drink.getStrDrinkThumb().toString());
        drink.setStrDrinkThumb(URI.create(s));
        drink.setDateModified(LocalDateTime.now());
        if (drink.getIngredient3().isBlank()) {
            drink.setIngredient3(null);
        }
        if (drink.getIngredient4().isBlank()) {
            drink.setIngredient4(null);
        }
        if (drink.getIngredient5().isBlank()) {
            drink.setIngredient5(null);
        }
        drink.setStatus(Status.ADDED);
        DrinkDAO drinkDAO = drinkMapper.mapDinkToDrinkDAO(drink);
        dbDrinkDAOManager.save(drinkDAO);
    }

    public Drink getDrink(int id) {
        return drinkDBService.getDrinkById(id);
    }

    public Drink getDrink(UUID uuid) {
        return drinkDBService.getDrinkByUUID(uuid);
    }

    public void deleteDrink(UUID id) {
        dbDrinkDAOManager.delete(id);
    }

    public List<Drink> search(String searchString) {
        return drinkDBService.getSearchResults(searchString);
//        if (searchString != null) {
//            return getDrinks().stream()
//                    .filter(drink -> drink.getDrink().toLowerCase().contains(searchString.toLowerCase()))
//                    .toList();
//        } else {
//            return new ArrayList<>();
//        }
    }

    public List<Drink> filter(Boolean isAlcoholic) {
        return drinkDBService.filter(isAlcoholic);
    }


    public void update(Drink drink) {
        UUID id = drink.getId();
        DrinkDAO drinkDAO = dbDrinkDAOManager.find(id);
        drinkDAO = drinkMapper.mapDinkToDrinkDAO(drink);
        drinkDAO.setDateModified(LocalDateTime.now());
        dbDrinkDAOManager.update(drinkDAO);
    }
}
