package com.infoshareacademy.responsibledrinkersweb;

import com.infoshareacademy.responsibledrinkersweb.service.DrinkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DBTest {


    @Autowired
    private DrinkService drinkService;

    @Test
    void saveUserTest() {
    }
}
