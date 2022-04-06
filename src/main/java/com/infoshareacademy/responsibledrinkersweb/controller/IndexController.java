package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.responsibledrinkersweb.sevice.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    private DrinkService drinkService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
