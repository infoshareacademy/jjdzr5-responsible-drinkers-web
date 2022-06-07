package com.infoshareacademy.responsibledrinkersweb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class DBInit implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application started");

    }
}
