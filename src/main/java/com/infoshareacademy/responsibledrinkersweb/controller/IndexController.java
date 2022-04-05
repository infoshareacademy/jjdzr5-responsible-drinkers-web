package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.responsibledrinkersweb.sevice.DateFormat;
import com.infoshareacademy.responsibledrinkersweb.sevice.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    private DrinkService drinkService;
    @Autowired
    private DateFormat dateFormat;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("newestdrinks", drinkService.getNewest(8));
        model.addAttribute("dateformat", dateFormat.getDatePatter());

        return "index";
    }
}
