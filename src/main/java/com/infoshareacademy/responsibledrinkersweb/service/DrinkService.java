package com.infoshareacademy.responsibledrinkersweb.service;

import com.infoshareacademy.drinkers.domain.drink.Alcoholic;
import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.domain.drink.Status;
import com.infoshareacademy.drinkers.service.manage.DrinkManager;
import com.infoshareacademy.responsibledrinkersweb.exceptions.ImageNotFound;
import com.infoshareacademy.responsibledrinkersweb.repository.DrinkRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DrinkService {

    private final List<Drink> drinkList = new DrinkRepository().getRepository();

    public List<Drink> getDrinks() {
        return drinkList;
    }

    public List<Drink> getNewest(int count) {
        return getDrinks().stream()
                .sorted(Comparator.comparing(Drink::getDateModified, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(count)
                .toList();
    }

    public List<Drink> getNewestAccepted(int count) {
        return getAcceptedDrinks().stream()
                .sorted(Comparator.comparing(Drink::getDateModified, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(count)
                .toList();
    }

    public List<Drink> getAcceptedDrinks() {
        return getDrinks().stream()
                .filter(drink -> drink.getStatus().equals(Status.ACCEPTED))
                .toList();
    }

    public void addDrink(Drink drink) {
        DrinkManager drinkManager = new DrinkManager(getDrinks());
        String url = ImageNotFound.verifyURL(drink.getDrinkThumb().toString());
        drink.setDrinkThumb(URI.create(url));
        if (drink.getIngredient3().isBlank()) {
            drink.setIngredient3(null);
        }
        if (drink.getIngredient4().isBlank()) {
            drink.setIngredient4(null);
        }
        if (drink.getIngredient5().isBlank()) {
            drink.setIngredient5(null);
        }
        drink.setDateModified(LocalDateTime.now());
        drinkManager.addDrinkToList(drink);
    }

    public Drink getDrink(int id) {
        return getDrinks()
                .stream()
                .filter((Drink d) ->
                        d.getIdDrink() == id
                )
                .findFirst()
                .orElse(new Drink());
    }

    public void deleteDrink(int id) {
        getDrinks().removeIf(drink -> drink.getIdDrink().equals(id));
    }

    public Drink findByID(int id) {
        return getDrinks().stream()
                .filter(drink -> drink.getIdDrink() == id)
                .findFirst()
                .orElse(new Drink());
    }

    public List<Drink> search(String searchString) {
        if (searchString != null) {
            return getDrinks().stream()
                    .filter(drink -> drink.getDrink().toLowerCase().contains(searchString.toLowerCase()))
                    .toList();
        } else {
            return new ArrayList<>();
        }
    }

    public List<Drink> filter(Boolean isAlcoholic) {
        if (isAlcoholic) {
            return getDrinks().stream()
                    .filter(drink -> drink.getAlcoholic().equals(Alcoholic.ALCOHOLIC.getName()))
                    .toList();
        } else {
            return getDrinks().stream()
                    .filter(drink -> drink.getAlcoholic().equals(Alcoholic.NON_ALCOHOLIC.getName()))
                    .toList();
        }
    }
}
