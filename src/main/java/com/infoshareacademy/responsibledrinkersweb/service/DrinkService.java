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

@Service
public class DrinkService {

    private final List<Drink> drinkList = new DrinkRepository().getRepository();
    private DBDrinkDAOManager dbDrinkDAOManager;
    private DrinkMapper drinkMapper;
    private DrinkDBService drinkDBService;

    public DrinkService(DBDrinkDAOManager dbDrinkDAOManager, DrinkMapper drinkMapper, DrinkDBService drinkDBService) {
        this.dbDrinkDAOManager = dbDrinkDAOManager;
        this.drinkMapper = drinkMapper;
        this.drinkDBService = drinkDBService;
    }

    public List<Drink> getDrinks() {
        List<DrinkDAO> drinkDAOList = dbDrinkDAOManager.findAll();
        List<Drink> drinks = drinkMapper.mapToDrinkList(drinkDAOList);
        return drinks;
//        return drinkList;
    }

    public List<Drink> getNewest(int count) {
        return drinkDBService.getNewest(count);
//        return getDrinks().stream()
//                .sorted(Comparator.comparing(Drink::getDateModified, Comparator.nullsLast(Comparator.reverseOrder())))
//                .limit(count)
//                .toList();
    }

    public List<Drink> getNewestAccepted(int count) {
        return drinkDBService.getNewestAccepted(count);
//        return getAcceptedDrinks().stream()
//                .sorted(Comparator.comparing(Drink::getDateModified, Comparator.nullsLast(Comparator.reverseOrder())))
//                .limit(count)
//                .toList();
    }

    public List<Drink> getAcceptedDrinks() {
        return drinkDBService.getAcceptedDrinks();
//        return getDrinks().stream()
//                .filter(drink -> drink.getStatus().equals(Status.ACCEPTED))
//                .toList();
    }

    public void addDrink(Drink drink) {
        String s = ImageNotFound.verifyURL(drink.getStrDrinkThumb().toString());
        drink.setStrDrinkThumb(URI.create(s));
        drink.setDateModified(LocalDateTime.now());
        drink.setStatus(Status.ADDED);
        DrinkDAO drinkDAO = drinkMapper.mapDinkToDrinkDAO(drink);
        dbDrinkDAOManager.save(drinkDAO);
//        DrinkManager drinkManager = new DrinkManager(getDrinks());
//        String url = ImageNotFound.verifyURL(drink.getDrinkThumb().toString());
//        drink.setDrinkThumb(URI.create(url));
//        if (drink.getIngredient3().isBlank()) {
//            drink.setIngredient3(null);
//        }
//        if (drink.getIngredient4().isBlank()) {
//            drink.setIngredient4(null);
//        }
//        if (drink.getIngredient5().isBlank()) {
//            drink.setIngredient5(null);
//        }
//        drink.setDateModified(LocalDateTime.now());
//        drinkManager.addDrinkToList(drink);
}

    public Drink getDrink(int id) {
        return drinkDBService.getDrinkById(id);
//        return getDrinks()
//                .stream()
//                .filter((Drink d) ->
//                        d.getIdDrink() == id
//                )
//                .findFirst()
//                .orElse(new Drink());
    }

    public void deleteDrink(int id) {
        dbDrinkDAOManager.delete(id);
//        getDrinks().removeIf(drink -> drink.getIdDrink().equals(id));
    }

    public Drink findByID(int id) {
        return drinkDBService.getDrinkById(id);
//        return getDrinks().stream()
//                .filter(drink -> drink.getIdDrink() == id)
//                .findFirst()
//                .orElse(new Drink());
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
//        if (isAlcoholic) {
//            return getDrinks().stream()
//                    .filter(drink -> drink.getAlcoholic().equals(Alcoholic.ALCOHOLIC.getName()))
//                    .toList();
//        } else {
//            return getDrinks().stream()
//                    .filter(drink -> drink.getAlcoholic().equals(Alcoholic.NON_ALCOHOLIC.getName()))
//                    .toList();
//        }
    }


}
