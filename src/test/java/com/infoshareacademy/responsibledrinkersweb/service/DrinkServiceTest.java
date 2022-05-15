package com.infoshareacademy.responsibledrinkersweb.service;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.domain.drink.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DrinkServiceTest {

    @Autowired
    public DrinkService drinkService;

    @Test
    void getAcceptedDrinks_returnsValidDrinks() {
        List<Drink> drinkList = new ArrayList<>();
        Drink d1 = new Drink();
        d1.setStatus(Status.ADDED);
        List<Drink> acceptedDrinks = drinkService.getAcceptedDrinks();
        assertThat(acceptedDrinks).containsOnly(d1);
    }
}