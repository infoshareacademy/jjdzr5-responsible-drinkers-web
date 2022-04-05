package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.responsibledrinkersweb.sevice.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    private DrinkService drinkService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("newestdrinks", drinkService.getNewest(8));
        return "index";
    }
}
