package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.responsibledrinkersweb.sevice.DateFormat;
import com.infoshareacademy.responsibledrinkersweb.sevice.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MoreInfoController {

    @Autowired
    private DrinkService drinkService;
    @Autowired
    private DateFormat dateFormat;

    @RequestMapping("/show-more")
    public String showmore(@RequestParam int id, Model model) {
        model.addAttribute("drink", drinkService.getDrink(id));
        model.addAttribute("dateformat", dateFormat.getDatePatter());
        return "show-more";
    }
}
