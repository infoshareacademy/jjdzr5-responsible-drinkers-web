package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.service.searching.Search;
import com.infoshareacademy.drinkers.service.sorting.SortDrinks;
import com.infoshareacademy.drinkers.service.sorting.SortItems;
import com.infoshareacademy.responsibledrinkersweb.sevice.DateFormat;
import com.infoshareacademy.responsibledrinkersweb.sevice.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private DrinkService drinkService;
    @Autowired
    private DateFormat dateFormat;

    private static final Integer ELEMENTS_TO_PRINT = 8;

    @GetMapping(path = {"/", "/index"})
    public String main(Model model) {
        model.addAttribute("newestdrinks", drinkService.getNewest(ELEMENTS_TO_PRINT));
        model.addAttribute("dateformat", dateFormat.getDatePatter());
        return "index";
    }

    @GetMapping("/drink_list")
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

    @GetMapping("/add_new_drink")
    public String addDrink(Model model, Drink drink) {
        model.addAttribute("drink", new Drink());
        return "add_new_drink";
    }

    @GetMapping("delete-drink")
    public RedirectView delete(@RequestParam int id) {
        drinkService.deleteDrink(id);
        return new RedirectView("/drink_list?sort=0");
    }

    @GetMapping("/manager")
    public String manager(Model model) {
        return "manager";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        return "signup";
    }

    @GetMapping("/account_settings")
    public String account(Model model) {
        return "account_settings";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam String keyword) {
        Integer id;
        try {
            id = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            id = null;
        }
        List<Drink> result = new Search(drinkService.getDrinks())
                .searchByName(keyword)
                .searchByID(id)
                .getResults();
        model.addAttribute("result", result);
        model.addAttribute("keyword", keyword);
        return "search";
    }
}
