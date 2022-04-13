package com.infoshareacademy.responsibledrinkersweb.sevice;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.service.manage.DrinkManager;
import com.infoshareacademy.responsibledrinkersweb.repository.DrinkRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Component
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

    public void addDrink(Drink drink) {
        DrinkManager drinkManager = new DrinkManager(drinkList);
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
}
