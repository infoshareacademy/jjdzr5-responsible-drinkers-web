package com.infoshareacademy.responsibledrinkersweb.service;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.List;


class DrinkServiceTest {

    @Test
    void getNewest() {
    }

    @Test
    void getNewestAccepted_returnsListOfCorrectSize_givenInteger() {
        DrinkService drinkService = new DrinkService();
        List<Drink> drinkList = drinkService.getNewestAccepted(6);
        int actualResult = drinkList.size();
        Assertions.assertEquals(6, actualResult);
    }

    @Test
    void getAcceptedDrinks() {
    }

    @Test
    void deleteDrink() {
    }

    @Test
    void findByID() {
    }
}