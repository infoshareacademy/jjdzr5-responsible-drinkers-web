package com.infoshareacademy.responsibledrinkersweb;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.responsibledrinkersweb.entity.DrinkDAO;
import com.infoshareacademy.responsibledrinkersweb.entity.control.DBDrinkDAOManager;
import com.infoshareacademy.responsibledrinkersweb.mapper.DrinkMapper;
import com.infoshareacademy.responsibledrinkersweb.repository.DrinkRepository;
import com.infoshareacademy.responsibledrinkersweb.service.DrinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBInit implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBInit.class);


    private DrinkService drinkService;
    private DBDrinkDAOManager dbDrinkDAOManager;
    private DrinkMapper drinkMapper;
    private final List<Drink> drinkList = new DrinkRepository().getRepository();


    public DBInit(DrinkService drinkService, DBDrinkDAOManager dbDrinkDAOManager, DrinkMapper drinkMapper) {
        this.drinkService = drinkService;
        this.dbDrinkDAOManager = dbDrinkDAOManager;
        this.drinkMapper = drinkMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        List<DrinkDAO> drinkDAOS = drinkMapper.mapToDrinkDAOList(drinkList);
        dbDrinkDAOManager.saveAll(drinkDAOS);
        LOGGER.info("***    DB initialized!     ***");
        LOGGER.info("***  Application started!  ***");

    }
}
