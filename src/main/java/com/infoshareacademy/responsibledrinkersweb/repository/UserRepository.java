package com.infoshareacademy.responsibledrinkersweb.repository;

import com.infoshareacademy.responsibledrinkersweb.entity.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserDAO, UUID> {


}
