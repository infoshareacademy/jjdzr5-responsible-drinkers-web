package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.drinkers.domain.drink.Alcoholic;
import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.service.filtering.FilterElements;
import com.infoshareacademy.drinkers.service.filtering.FilterList;
import com.infoshareacademy.drinkers.service.sorting.SortDrinks;
import com.infoshareacademy.drinkers.service.sorting.SortItems;
import com.infoshareacademy.responsibledrinkersweb.sevice.DateFormat;
import com.infoshareacademy.responsibledrinkersweb.sevice.DrinkService;
import com.infoshareacademy.responsibledrinkersweb.sevice.ListParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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

    @PostMapping("/list")
    public String list(@ModelAttribute ListParameter parameter, Model model) {
        System.out.println("test");
        System.out.println(parameter);

        List<Drink> drinkList = drinkService.search(parameter.getKeyword());

        if (parameter.getAlcoholic()!=null) {
            drinkList = drinkList.stream().filter(drink -> drink.getAlcoholic().equalsIgnoreCase(parameter.getAlcoholic().getName())).toList();
        }
        if (parameter.getFilterElements()!=null) {
            FilterList filterList = new FilterList(drinkList);
            drinkList = filterList.getFilteredByIngredient(parameter.getFilterElements()).getResults();
        }

        System.out.println("test");
        System.out.println(parameter);
        model.addAttribute("listparameter",parameter);
        model.addAttribute("drinklist", drinkList);
        model.addAttribute("dateformat", dateFormat.getDatePatter());

        return "drink_list";
    }

    @GetMapping("/drink_list")
    public String drinkList(Model model, @RequestParam(value = "sort", required = false, defaultValue = "0") int sort,
                            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                            @RequestParam(value = "f", required = false, defaultValue = "") Alcoholic f,
                            @RequestParam(value = "type", required = false, defaultValue = "") FilterElements type) {

        List<Drink> drinkList = drinkService.search(keyword);

        if (f != null) {
            if (f.equals(Alcoholic.ALCOHOLIC)) {
                drinkList = drinkService.filter(true);
            } else if (f.equals(Alcoholic.NON_ALCOHOLIC)) {
                drinkList = drinkService.filter(false);
            }
        }

        if (type != null) {
            FilterList filterList = new FilterList(drinkList);
            drinkList = filterList.getFilteredByIngredient(type).getResults();
        }

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
        model.addAttribute("listparameter",new ListParameter());
        return "drink_list";
    }

    @GetMapping(value = "/add_new_drink")
    public String addNewDrink(Model model, Drink drink) {
        int nextId = drinkService.getDrinks().stream()
                .sorted((Drink o1, Drink o2) -> o2.getIdDrink() - o1.getIdDrink())
                .limit(1)
                .findFirst()
                .orElse(new Drink())
                .getIdDrink() + 1;
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

    @PostMapping("/modify")
    public String modify(@Valid @ModelAttribute Drink drink, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "edit";
        } else {
            drinkService.deleteDrink(drink.getIdDrink());
            drinkService.addDrink(drink);
            model.addAttribute("drink", drink);
            model.addAttribute("dateformat", dateFormat.getDatePatter());
            return "modify";
        }
    }

    @GetMapping("edit")
    public String edit(@RequestParam int id, Model model) {
        Drink drink = drinkService.getDrink(id);
        model.addAttribute("drink", drink);
        return "edit";
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

    @RequestMapping("/show-more")
    public String showMore(@RequestParam int id, Model model) {
        model.addAttribute("drink", drinkService.getDrink(id));
        model.addAttribute("dateformat", dateFormat.getDatePatter());
        return "show-more";
    }
}
