package com.infoshareacademy.responsibledrinkersweb;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.responsibledrinkersweb.domain.Gender;
import com.infoshareacademy.responsibledrinkersweb.dto.CreateUserDto;
import com.infoshareacademy.responsibledrinkersweb.dto.UserDto;
import com.infoshareacademy.responsibledrinkersweb.entity.DrinkDAO;
import com.infoshareacademy.responsibledrinkersweb.entity.UserDAO;
import com.infoshareacademy.responsibledrinkersweb.entity.control.DBDrinkDAOManager;
import com.infoshareacademy.responsibledrinkersweb.mapper.DrinkMapper;
import com.infoshareacademy.responsibledrinkersweb.repository.DrinkRepository;
import com.infoshareacademy.responsibledrinkersweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DBInit implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBInit.class);


    private final DBDrinkDAOManager dbDrinkDAOManager;
    private final DrinkMapper drinkMapper;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final List<Drink> drinkList = new DrinkRepository().getRepository();

    @Override
    public void run(String... args) throws Exception {

        CreateUserDto createUserDto1 = new CreateUserDto("user", Gender.FEMALE, "a@b.pl", passwordEncoder.encode("user"),
                LocalDate.of(2000, Month.FEBRUARY, 1), "USER");
        userService.addUser(createUserDto1);
        CreateUserDto createUserDto2 = new CreateUserDto("admin", Gender.MALE, "x@z.pl", passwordEncoder.encode("admin"),
                LocalDate.of(1999, Month.NOVEMBER, 21), "ADMIN");
        UserDto adminDto = userService.addUser(createUserDto2);
        CreateUserDto createUserDto3 = new CreateUserDto("new", Gender.MALE, "new@wp.pl", passwordEncoder.encode("new"),
                LocalDate.of(1991, Month.APRIL, 12), "REGISTERED");
        userService.addUser(createUserDto3);
        UserDAO userDAO = modelMapper.map(adminDto, UserDAO.class);

        List<DrinkDAO> drinkDAOS = drinkMapper.mapToDrinkDAOList(drinkList);
        drinkDAOS.forEach(drinkDAO -> drinkDAO.setUserDAO(userDAO));
        dbDrinkDAOManager.saveAll(drinkDAOS);

        LOGGER.info("***    DB initialized!     ***");
        LOGGER.info("***  Application started!  ***");
    }
}
