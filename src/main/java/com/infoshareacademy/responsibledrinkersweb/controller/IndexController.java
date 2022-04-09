package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.service.sorting.SortDrinks;
import com.infoshareacademy.drinkers.service.sorting.SortItems;
import com.infoshareacademy.responsibledrinkersweb.sevice.DateFormat;
import com.infoshareacademy.responsibledrinkersweb.sevice.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    public String drinkList(Model model, @RequestParam int sort) {
        List<Drink> drinkList = drinkService.getDrinks();
        SortDrinks sortDrinks = new SortDrinks(drinkList);
        if (sort == 1) {
            model.addAttribute("drinklist", sortDrinks.getSortedList(SortItems.ID));
        } else if (sort == 2) {
            model.addAttribute("drinklist", sortDrinks.getSortedList(SortItems.DRINK_NAME));
        } else if (sort == 4) {
            model.addAttribute("drinklist", sortDrinks.getSortedList(SortItems.ALCOHOLIC));
        } else if (sort == 3) {
            model.addAttribute("drinklist", sortDrinks.getSortedList(SortItems.DATE));
        } else {
            model.addAttribute("drinklist", drinkList);
        }
        model.addAttribute("dateformat", dateFormat.getDatePatter());
        return "drink_list";
    }

    @RequestMapping("/add_new_drink")
    public String addDrink(Model model) {
        return "add_new_drink";
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
