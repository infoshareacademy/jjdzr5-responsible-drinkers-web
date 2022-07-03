package com.infoshareacademy.responsibledrinkersweb.service;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.domain.drink.Status;
import com.infoshareacademy.responsibledrinkersweb.domain.ListParameter;
import com.infoshareacademy.responsibledrinkersweb.entity.DrinkDAO;
import com.infoshareacademy.responsibledrinkersweb.entity.control.DBDrinkDAOManager;
import com.infoshareacademy.responsibledrinkersweb.exceptions.ImageNotFound;
import com.infoshareacademy.responsibledrinkersweb.mapper.DrinkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DBDrinkDAOManager dbDrinkDAOManager;
    private final DrinkMapper drinkMapper;
    private final DrinkDBService drinkDBService;


    public List<Drink> getDrinks() {
        List<DrinkDAO> drinkDAOList = dbDrinkDAOManager.findAll();
        return drinkMapper.mapToDrinkList(drinkDAOList);
    }

    public List<Drink> getNewestAccepted(int count) {
        return drinkDBService.getNewestAccepted(count);
    }

    public List<Drink> getAcceptedDrinks() {
        return drinkDBService.getAcceptedDrinks("idDrink");
    }

    public void addDrink(Drink drink) {
        String s = ImageNotFound.verifyURL(drink.getStrDrinkThumb().toString());
        drink.setStrDrinkThumb(URI.create(s));
        drink.setDateModified(LocalDateTime.now());
        if (drink.getIngredient3().isBlank()) {
            drink.setIngredient3(null);
        }
        if (drink.getIngredient4().isBlank()) {
            drink.setIngredient4(null);
        }
        if (drink.getIngredient5().isBlank()) {
            drink.setIngredient5(null);
        }
        drink.setStatus(Status.ADDED);
        DrinkDAO drinkDAO = drinkMapper.mapDinkToDrinkDAO(drink);
        dbDrinkDAOManager.save(drinkDAO);
    }

    public Drink getDrink(int id) {
        return drinkDBService.getDrinkById(id);
    }

    public Drink getDrink(UUID uuid) {
        return drinkDBService.getDrinkByUUID(uuid);
    }

    public void deleteDrink(UUID id) {
        dbDrinkDAOManager.delete(id);
    }

    public List<Drink> getSearchAndFilterAcceptedDrinksResult(ListParameter parameter, Status status) {
        setOrderParameterToSQL(parameter);
        if (parameter.getKeyword() != null && parameter.getAlcoholic() != null && parameter.getFilterElements() != null) {
            return drinkDBService.getSearchAndFilterResult(parameter.getSort(), parameter.getKeyword(), parameter.getAlcoholic(), parameter.getFilterElements(), status);
        } else if (parameter.getKeyword() != null && parameter.getAlcoholic() != null) {
            return drinkDBService.getSearchAndFilterResult(parameter.getSort(), parameter.getKeyword(), parameter.getAlcoholic(), status);
        } else if (parameter.getAlcoholic() != null && parameter.getKeyword() != null) {
            return drinkDBService.getAlcoholicResults(parameter.getSort(), parameter.getAlcoholic(), parameter.getKeyword(), status);
        } else if (parameter.getFilterElements() != null && parameter.getKeyword() != null) {
            return drinkDBService.getFilterResults(parameter.getSort(), parameter.getFilterElements(), parameter.getKeyword(), status);
        } else if (parameter.getKeyword() != null) {
            return drinkDBService.getSearchResults(parameter.getSort(), parameter.getKeyword(), status);
        } else {
            return drinkDBService.getAllDrinksByStatus(parameter.getSort(), status);
        }
    }

    public void update(Drink drink) {
        DrinkDAO drinkDAO;
        drinkDAO = drinkMapper.mapDinkToDrinkDAO(drink);
        drinkDAO.setDateModified(LocalDateTime.now());
        dbDrinkDAOManager.update(drinkDAO);
    }

    public List<Drink> getSearchAndFilterAllDrinksResult(ListParameter parameter) {
        setOrderParameterToSQL(parameter);
        if (parameter.getKeyword() == null) {
            parameter.setKeyword("");
        }

        if (parameter.getAlcoholic() != null && parameter.getFilterElements() != null && parameter.getStatus() != null) {
            return drinkDBService.getSearchAndFilterResult(parameter.getSort(), parameter.getKeyword(), parameter.getAlcoholic(), parameter.getFilterElements(), parameter.getStatus());
        } else if (parameter.getAlcoholic() != null && parameter.getFilterElements() == null && parameter.getStatus() == null) {
            return drinkDBService.getSearchAndFilterResult(parameter.getSort(), parameter.getKeyword(), parameter.getAlcoholic());
        } else if (parameter.getAlcoholic() != null && parameter.getFilterElements() != null && parameter.getStatus() == null) {
            return drinkDBService.getSearchAndFilterResult(parameter.getSort(), parameter.getKeyword(), parameter.getAlcoholic(), parameter.getFilterElements());
        } else if (parameter.getAlcoholic() != null && parameter.getFilterElements() == null && parameter.getStatus() != null) {
            return drinkDBService.getSearchAndFilterResult(parameter.getSort(), parameter.getKeyword(), parameter.getAlcoholic(), parameter.getStatus());
        } else if (parameter.getAlcoholic() == null && parameter.getFilterElements() != null && parameter.getStatus() != null) {
            return drinkDBService.getSearchAndFilterResult(parameter.getSort(), parameter.getKeyword(), parameter.getFilterElements(), parameter.getStatus());
        } else if (parameter.getAlcoholic() == null && parameter.getFilterElements() != null && parameter.getStatus() == null) {
            return drinkDBService.getSearchAndFilterResult(parameter.getSort(), parameter.getKeyword(), parameter.getFilterElements());
        } else if (parameter.getAlcoholic() == null && parameter.getFilterElements() == null && parameter.getStatus() != null) {
            return drinkDBService.getSearchAndFilterResult(parameter.getSort(), parameter.getKeyword(), parameter.getStatus());
        } else if (parameter.getAlcoholic() == null && parameter.getFilterElements() == null && parameter.getStatus() == null) {
            return drinkDBService.getSearchAndFilterResult(parameter.getSort(), parameter.getKeyword());
        } else {
            return drinkDBService.getAllDrinks(parameter.getSort());
        }
    }

    private void setOrderParameterToSQL(ListParameter parameter) {
        if (parameter.getSort() == null) {
            parameter.setSort("idDrink");
        } else {
            switch (parameter.getSort()) {
                case "NAME":
                    parameter.setSort("strDrink");
                    break;
                case "TYPE":
                    parameter.setSort("strAlcoholic");
                    break;
                case "DATE":
                    parameter.setSort("dateModified");
                    break;
                case "STATUS":
                    parameter.setSort("status");
                    break;
                default:
                    parameter.setSort("idDrink");
                    break;
            }
        }
    }
}
