package com.infoshareacademy.responsibledrinkersweb.repository;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.service.gson.JsonReader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

public class DrinkRepository {

    private final List<Drink> drinkList = new JsonReader().getDrinkList();

    public List<Drink> getRepository() {
        return drinkList;
    }
}
