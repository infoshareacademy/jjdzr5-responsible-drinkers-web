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

    private static final Integer ELEMENTS_TO_PRINT = 8;

    @RequestMapping("/")
    public String main(Model model) {
        model.addAttribute("newestdrinks", drinkService.getNewest(ELEMENTS_TO_PRINT));
        model.addAttribute("dateformat", dateFormat.getDatePatter());
        return "index";
    }

    @RequestMapping("/index.html")
    public String index(Model model) {
        model.addAttribute("newestdrinks", drinkService.getNewest(ELEMENTS_TO_PRINT));
        model.addAttribute("dateformat", dateFormat.getDatePatter());
        return "index";
    }

    @RequestMapping("/drink_list.html")
    public String drinkList(Model model) {
        model.addAttribute("drinklist",drinkService.getDrinks());
        model.addAttribute("dateformat", dateFormat.getDatePatter());
        return "drink_list";
    }

    @RequestMapping("/add_new_drink.html")
    public String addDrink(Model model) {
        return "add_new_drink";
    }

    @RequestMapping("/manager.html")
    public String manager(Model model) {
        return "manager";
    }

    @RequestMapping("/login.html")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/signup.html")
    public String signup(Model model) {
        return "signup";
    }

    @RequestMapping("/account_settings.html")
    public String account(Model model) {
        return "account_settings";
    }
}
