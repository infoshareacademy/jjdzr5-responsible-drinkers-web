package com.infoshareacademy.responsibledrinkersweb;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.domain.drink.Status;
import com.infoshareacademy.responsibledrinkersweb.entity.DrinkDAO;
import com.infoshareacademy.responsibledrinkersweb.entity.control.DBDrinkDAOManager;
import com.infoshareacademy.responsibledrinkersweb.mapper.DrinkMapper;
import com.infoshareacademy.responsibledrinkersweb.service.DrinkService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class DrinkDBTest {

    @Autowired
    private DrinkService drinkService;
    @Autowired
    private DBDrinkDAOManager dbDrinkDAOManager;
    @Autowired
    private DrinkMapper drinkMapper;


    @BeforeEach
    void setUp() {
        List<Drink> drinks = drinkService.getDrinks();
        List<DrinkDAO> drinkDAOS = drinkMapper.mapToDrinkDAOList(drinks);
        dbDrinkDAOManager.saveAll(drinkDAOS);
    }

    @AfterEach
    void tearDown() {
        dbDrinkDAOManager.deleteAll();
    }

    @Test
    void saveDrinksToDBTest() {
        List<Drink> drinks = drinkService.getDrinks();
//        List<DrinkDAO> drinkDAOS = drinkMapper.mapToDrinkDAOList(drinks);
//        dbDrinkDAOManager.saveAll(drinkDAOS);
        Assertions.assertThat(dbDrinkDAOManager.findAll()).hasSize(drinks.size());
    }

    @Test
    void readDrinksFromDBTest() {
        List<DrinkDAO> drinkDAOList = dbDrinkDAOManager.findAll();
        List<Drink> drinks = drinkMapper.mapToDrinkList(drinkDAOList);
        Assertions.assertThat(drinks).hasSize(drinkDAOList.size());
    }

    @Test
    void updateDrinkDAOSetStatusToDeletedTest() {
        List<DrinkDAO> drinkDAOList = dbDrinkDAOManager.findAll();
        for (DrinkDAO drinkDAO : drinkDAOList) {
            drinkDAO.setStatus(Status.DELETED);
            dbDrinkDAOManager.update(drinkDAO);
        }
        List<DrinkDAO> drinkDAOListAfterUpdate = dbDrinkDAOManager.findAll();
        for (DrinkDAO drinkDAO : drinkDAOListAfterUpdate) {
            Assertions.assertThat(drinkDAO.getStatus()).isEqualTo(Status.DELETED);
        }
    }

    @Test
    void deleteDrinkDAO() {
        List<DrinkDAO> drinkDAOList = dbDrinkDAOManager.findAll();
        UUID id = drinkDAOList.get(1).getId();
        dbDrinkDAOManager.deleteByUUID(id);
        Assertions.assertThat(dbDrinkDAOManager.find(id)).isNull();
    }

    @Test
    void emptyDrinkDBTest() {
        dbDrinkDAOManager.deleteAll();
        List<DrinkDAO> drinkDAOList = dbDrinkDAOManager.findAll();
        Assertions.assertThat(drinkDAOList).hasSize(0);
    }
}
