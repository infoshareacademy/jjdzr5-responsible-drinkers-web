package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.responsibledrinkersweb.sevice.DateFormat;
import com.infoshareacademy.responsibledrinkersweb.sevice.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("newestdrinks", drinkService.getNewest(ELEMENTS_TO_PRINT));
        model.addAttribute("dateformat", dateFormat.getDatePatter());
        return "index";
    }

    @RequestMapping("/drink_list")
    public String drinkList(Model model) {
        model.addAttribute("drinklist", drinkService.getDrinks());
        model.addAttribute("dateformat", dateFormat.getDatePatter());
        return "drink_list";
    }

    @RequestMapping("/add_new_drink")
    public String addNewDrink(Model model) {
        int nextId = drinkService.getDrinks().stream()
                .sorted((Drink o1, Drink o2) -> o2.getIdDrink() - o1.getIdDrink())
                .limit(1)
                .findFirst()
                .orElse(new Drink())
                .getIdDrink() + 1;
        Drink drink = new Drink();
        drink.setIdDrink(nextId);
        model.addAttribute("drink", drink);
        return "add_new_drink";
    }

    @PostMapping("/new_drink")
    public String newDrink(@Valid @ModelAttribute Drink drink, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_new_drink";
        } else {
            drinkService.addDrink(drink);
            model.addAttribute("dateformat", dateFormat.getDatePatter());
            model.addAttribute("drink", drink);
            return "new_drink";
        }
    }

    @RequestMapping("/manager")
    public String manager(Model model) {
        return "manager";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        return "signup";
    }

    @RequestMapping("/account_settings")
    public String account(Model model) {
        return "account_settings";
    }
}
