package com.infoshareacademy.responsibledrinkersweb.service;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.domain.drink.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DrinkServiceTest {

    @InjectMocks
    private DrinkService drinkService;

    @Test
    void getDrinks_shouldReturnDrinkList() {
        List<Drink> drinkList = drinkService.getDrinks();
        assertThat(drinkList).isNotNull();
        assertThat(drinkList).isNotEmpty();
    }

    @Test
    void getAcceptedDrinks_returnsValidDrinks() {
        // TODO: test do zrobienia
        // TODO: test nie działa poprawnie
        Drink d1 = new Drink();
        d1.setStatus(Status.ADDED);
        List<Drink> acceptedDrinks = drinkService.getAcceptedDrinks();
        assertThat(acceptedDrinks).containsOnly(d1);
    }
}