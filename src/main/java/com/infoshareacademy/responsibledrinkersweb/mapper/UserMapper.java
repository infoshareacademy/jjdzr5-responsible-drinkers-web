package com.infoshareacademy.responsibledrinkersweb.mapper;

import com.infoshareacademy.responsibledrinkersweb.dto.UserDto;
import com.infoshareacademy.responsibledrinkersweb.entity.UserDAO;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDAO mapToUserDAO(UserDto userDto) {
        UserDAO userDAO = new UserDAO();
        userDAO.setId(userDto.getId());
        userDAO.setUserName(userDto.getUserName());
        userDAO.setGender(userDto.getGender());
        userDAO.setEmail(userDto.getEmail());
        userDAO.setPassword(userDto.getPassword());
        userDAO.setDateOfBirth(userDto.getDateOfBirth());
        userDAO.setRole(userDto.getRole());
        userDAO.setActive(userDto.isActive());
        return userDAO;
    }
}
