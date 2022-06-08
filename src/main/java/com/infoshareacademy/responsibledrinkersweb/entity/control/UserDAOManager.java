package com.infoshareacademy.responsibledrinkersweb.entity.control;

import com.infoshareacademy.responsibledrinkersweb.entity.UserDAO;

import java.util.UUID;

public interface UserDAOManager {
    void save(UserDAO user);

    UserDAO find(UUID id);

}
