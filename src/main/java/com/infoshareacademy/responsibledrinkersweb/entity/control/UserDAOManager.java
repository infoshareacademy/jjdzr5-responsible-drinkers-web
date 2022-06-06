package com.infoshareacademy.responsibledrinkersweb.entity.control;

import com.infoshareacademy.responsibledrinkersweb.entity.UserDAO;

public interface UserDAOManager {
    void save(UserDAO user);

    UserDAO find(Long id);

    UserDAO update(UserDAO user);

    void delete(UserDAO user);
}
