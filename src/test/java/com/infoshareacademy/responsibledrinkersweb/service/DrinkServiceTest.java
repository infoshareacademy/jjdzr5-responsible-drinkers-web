package com.infoshareacademy.responsibledrinkersweb.service;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.domain.drink.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DrinkServiceTest {

    @InjectMocks
    private DrinkService drinkService;

    @Test
    void getAcceptedDrinks_returnsExpectedDrinks() {
        // given
        List<Drink> expectedResult = drinkService.getDrinks().stream()
                .filter(drink -> drink.getStatus() == Status.ACCEPTED)
                .toList();
        // when
        List<Drink> actualResult = drinkService.getAcceptedDrinks();
        // then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void getAcceptedDrinks_shouldReturnsDrinkList() {
        // given
        // when
        List<Drink> actualResult = drinkService.getDrinks();
        // then
        assertThat(actualResult).isInstanceOf(List.class).isNotNull();
    }


    @Test
    void getNewestAccepted_returnsListOfCorrectSize_givenInteger() {
        DrinkService drinkService = new DrinkService();
        List<Drink> drinkList = drinkService.getNewestAccepted(6);
        int actualResult = drinkList.size();
        Assertions.assertEquals(6, actualResult);
    }
}