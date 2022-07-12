package com.infoshareacademy.responsibledrinkersweb.mapper;

import com.infoshareacademy.responsibledrinkersweb.dto.UserDto;
import com.infoshareacademy.responsibledrinkersweb.entity.UserDAO;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDAO mapToUserDAO(UserDto userDto) {
        return new UserDAO(userDto.getUserName(), userDto.getGender(), userDto.getEmail(),
                userDto.getPassword(), userDto.getDateOfBirth(), userDto.getRole(), userDto.isActive());
    }
}
