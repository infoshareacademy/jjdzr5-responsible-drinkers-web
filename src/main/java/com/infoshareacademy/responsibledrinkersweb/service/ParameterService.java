package com.infoshareacademy.responsibledrinkersweb.service;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.service.filtering.FilterList;
import com.infoshareacademy.drinkers.service.sorting.SortDrinks;
import com.infoshareacademy.drinkers.service.sorting.SortItems;
import com.infoshareacademy.responsibledrinkersweb.domain.ListParameter;
import org.springframework.stereotype.Service;

import java.util.List;

public class ParameterService {

    public static List<Drink> func(ListParameter parameter, List<Drink> drinkList) {
        List<Drink> modifyList = drinkList;
        if (parameter.getKeyword() != null) {
            modifyList = modifyList.stream()
                    .filter(drink -> drink.getDrink().toLowerCase().contains(parameter.getKeyword().toLowerCase())).
                    toList();
//            modifyList = drinkService.search(parameter.getKeyword());
        }
        if (parameter.getAlcoholic() != null) {
            modifyList = modifyList.stream()
                    .filter(drink -> drink.getAlcoholic().equalsIgnoreCase(parameter.getAlcoholic().getName()))
                    .toList();
        }
        if (parameter.getFilterElements() != null) {
            FilterList filterList = new FilterList(modifyList);
            modifyList = filterList.getFilteredByIngredient(parameter.getFilterElements()).getResults();
        }
        if (parameter.getStatus() != null) {
            FilterList filterList = new FilterList(modifyList);
            modifyList = filterList.getFilteredByStatus(parameter.getStatus()).getResults();
        }
        SortDrinks sortDrinks = new SortDrinks(modifyList);
        if (parameter.getSort() != null) {
            modifyList = switch (parameter.getSort()) {
                case "ID" -> sortDrinks.getSortedList(SortItems.ID);
                case "NAME" -> sortDrinks.getSortedList(SortItems.DRINK_NAME);
                case "TYPE" -> sortDrinks.getSortedList(SortItems.ALCOHOLIC);
                case "DATE" -> sortDrinks.getSortedList(SortItems.DATE);
                default -> modifyList;
            };
        }
        return modifyList;
    }

}
