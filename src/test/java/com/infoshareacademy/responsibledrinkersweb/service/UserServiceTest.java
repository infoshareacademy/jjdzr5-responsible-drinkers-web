package com.infoshareacademy.responsibledrinkersweb.service;

import com.infoshareacademy.responsibledrinkersweb.domain.Gender;
import com.infoshareacademy.responsibledrinkersweb.dto.CreateUserDto;
import com.infoshareacademy.responsibledrinkersweb.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void addUserTest() {
        CreateUserDto createUserDto =
                new CreateUserDto("example", Gender.FEMALE, "example@mail.com", "passw0rd", LocalDate.now().minusYears(40), "USER");
        UserDto userDto = userService.addUser(createUserDto);
        Assertions.assertNotNull(userDto);
    }
}