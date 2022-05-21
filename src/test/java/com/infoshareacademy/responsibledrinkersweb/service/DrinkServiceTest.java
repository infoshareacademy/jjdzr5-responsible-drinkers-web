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
import static org.junit.jupiter.api.Assertions.assertAll;

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
        List<Drink> drinkList = drinkService.getNewestAccepted(6);
        assertThat(drinkList.size()).isEqualTo(6);
    }

    @Test
    void getNewestAccepted_returnsListOfDrinks() {
        List<Drink> actualResult = drinkService.getNewestAccepted(6);
        assertThat(actualResult).isInstanceOf(List.class).isNotNull();
    }

    @Test
    void getDrink_returnsNewDrink_givenNonExistingID() {
        Drink drink = drinkService.getDrink(1);
        assertThat(drink).isEqualTo(new Drink());
    }

    @Test
    void getDrink_returnsEmptyDrink_givenNonExistingID() {
        Drink drink = drinkService.getDrink(1);
        assertAll(
                () -> assertThat(drink.getIdDrink()).isZero(),
                () -> assertThat(drink.getStrDrink()).isNull(),
                () -> assertThat(drink.getIngredients()).isEmpty(),
                () -> assertThat(drink.getInstructions()).isNull(),
                () -> assertThat(drink.getAlcoholic()).isNull(),
                () -> assertThat(drink.getStrCategory()).isNull(),
                () -> assertThat(drink.getStrGlass()).isNull(),
                () -> assertThat(drink.getDateModified()).isNull()
        );
    }
}
