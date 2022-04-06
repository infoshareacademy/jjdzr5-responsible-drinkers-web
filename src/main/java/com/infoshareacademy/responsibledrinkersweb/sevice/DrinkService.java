package com.infoshareacademy.responsibledrinkersweb.sevice;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.responsibledrinkersweb.repository.DrinkRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DrinkService {

    public List<Drink> getDrinks() {
        return new DrinkRepository().getRepository();
    }

    public List<Drink> getNewest(int count) {
//        SortDrinks sortDrinks = new SortDrinks(getDrinks());
//        sortDrinks.getSortedList(SortItems.DATE);
//        return getDrinks().stream().sorted(new DrinkDateModifiedComparator()).limit(count).toList();
        return new ArrayList<>();
    }

    public void addDrink(Drink drink) {

    }

    public Drink getDrink(int id) {
        return new DrinkRepository().getRepository()
                .stream()
                .filter((Drink d) ->
                        d.getIdDrink() == id
                )
                .findFirst().orElse(new Drink());
    }
}
