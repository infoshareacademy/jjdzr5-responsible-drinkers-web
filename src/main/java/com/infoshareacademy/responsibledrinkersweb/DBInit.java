package com.infoshareacademy.responsibledrinkersweb;

import com.infoshareacademy.drinkers.domain.drink.Status;
import com.infoshareacademy.responsibledrinkersweb.domain.Gender;
import com.infoshareacademy.responsibledrinkersweb.entity.DrinkDAO;
import com.infoshareacademy.responsibledrinkersweb.entity.UserDAO;
import com.infoshareacademy.responsibledrinkersweb.entity.control.DBDrinkDAOManager;
import com.infoshareacademy.responsibledrinkersweb.entity.control.DBUserDAOManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DBInit implements CommandLineRunner {

    private DBUserDAOManager dbUserDAOManager;
    private DBDrinkDAOManager dbDrinkDAOManager;

    public DBInit(DBUserDAOManager dbUserDAOManager, DBDrinkDAOManager dbDrinkDAOManager) {
        this.dbUserDAOManager = dbUserDAOManager;
        this.dbDrinkDAOManager = dbDrinkDAOManager;
    }

    @Override
    public void run(String... args) throws Exception {
        UserDAO userDAO = new UserDAO("Jan", Gender.FEMALE, "e@mail.com", "123456", LocalDate.now().minusYears(3));
        dbUserDAOManager.save(userDAO);

        List<UserDAO> all = dbUserDAOManager.findAll();
        UUID id = all.get(3).getId();

        DrinkDAO drinkDAO = new DrinkDAO(1, "Piwo", "Alkohol", "Nie",
                "Piwo", "Instrukcje", "Piwo", "Ingredienty",
                LocalDateTime.now(), Status.ADDED);
        drinkDAO.setUserDAO(dbUserDAOManager.find(id));
        dbDrinkDAOManager.save(drinkDAO);

        DrinkDAO drinkDAO1 = dbDrinkDAOManager.findAll().get(2);
        drinkDAO1.setStatus(Status.MODIFIED);
        dbDrinkDAOManager.update(drinkDAO1);

    }
}
