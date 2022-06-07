package com.infoshareacademy.responsibledrinkersweb.mapper;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.responsibledrinkersweb.entity.DrinkDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Service
public class DrinkMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DrinkMapper.class);


    public List<Drink> mapToDrinkList(List<DrinkDAO> drinkDAOList) {
        return drinkDAOList.stream()
                .map(this::mapDrinkDAOToDrink)
                .toList();
    }

    public List<DrinkDAO> mapToDrinkDAOList(List<Drink> drinks) {
        return drinks.stream()
                .map(this::mapDinkToDrinkDAO)
                .toList();
    }

    public Drink mapDrinkDAOToDrink(DrinkDAO drinkDAO) {
        Drink drink = new Drink();
        drink.setIdDrink(drinkDAO.getIdDrink());
        drink.setDrinkName(drinkDAO.getStrDrink());
        drink.setCategory(drinkDAO.getStrCategory());
        setIngredientsFromDrinkDAO(drinkDAO, drink);
        drink.setAlcoholic(drinkDAO.getStrAlcoholic());
        drink.setGlass(drinkDAO.getStrGlass());
        try {
            drink.setStrDrinkThumb(new URI(drinkDAO.getStrDrinkThumb()));
        } catch (URISyntaxException e) {
            drink.setDrinkThumb(null);
            LOGGER.error("Error while setting URI for drink thumb", e);
        }
        drink.setDateModified(drinkDAO.getDateModified());
        drink.setStatus(drinkDAO.getStatus());
        drink.setInstructions(drinkDAO.getStrInstructions());
        return drink;
    }

    private void setIngredientsFromDrinkDAO(DrinkDAO drinkDAO, Drink drink) {
        int i = 0;
        for (String ingredient : getIngredientsListFromDrinkDAO(drinkDAO)) {
            if (i == 0) {
                drink.setIngredient1(ingredient);
            } else if (i == 1) {
                drink.setIngredient2(ingredient);
            } else if (i == 2) {
                drink.setIngredient3(ingredient);
            } else if (i == 3) {
                drink.setIngredient4(ingredient);
            } else if (i == 4) {
                drink.setIngredient5(ingredient);
            } else if (i == 5) {
                drink.setIngredient6(ingredient);
            } else if (i == 6) {
                drink.setIngredient7(ingredient);
            } else if (i == 7) {
                drink.setIngredient8(ingredient);
            } else if (i == 8) {
                drink.setIngredient9(ingredient);
            } else if (i == 9) {
                drink.setIngredient10(ingredient);
            }
            i++;
        }
    }

    public DrinkDAO mapDinkToDrinkDAO(Drink drink) {
        DrinkDAO drinkDAO = new DrinkDAO();
        drinkDAO.setIdDrink(drink.getIdDrink());
        drinkDAO.setStrDrink(drink.getStrDrink());
        drinkDAO.setStrCategory(drink.getStrCategory());
        drinkDAO.setStrIngredient(getIngredientsStringFromDrink(drink));
        drinkDAO.setStrAlcoholic(drink.getAlcoholic());
        drinkDAO.setStrGlass(drink.getStrGlass());
        drinkDAO.setStrDrinkThumb(drink.getStrDrinkThumb().toString());
        drinkDAO.setDateModified(drink.getDateModified());
        drinkDAO.setStatus(drink.getStatus());
        drinkDAO.setStrInstructions(drink.getStrInstructions());
        return drinkDAO;
    }

    private String getIngredientsStringFromDrink(Drink drink) {
        return drink.getStringIntredientrs();
    }

    private List<String> getIngredientsListFromDrinkDAO(DrinkDAO drinkDAO) {
        return Arrays.asList(drinkDAO.getStrIngredient().split(","));
    }

}
