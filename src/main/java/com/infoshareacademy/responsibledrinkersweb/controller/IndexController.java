package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.domain.drink.Status;
import com.infoshareacademy.responsibledrinkersweb.domain.ListParameter;
import com.infoshareacademy.responsibledrinkersweb.service.DateFormat;
import com.infoshareacademy.responsibledrinkersweb.service.DrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final DrinkService drinkService;
    private final DateFormat dateFormat;
    private static final Integer ELEMENTS_TO_PRINT = 8;

    @GetMapping(path = {"/", "/index"})
    public String main(Model model) {
        model.addAttribute("newestdrinks", drinkService.getNewestAccepted(ELEMENTS_TO_PRINT));
        model.addAttribute("dateformat", dateFormat.getDatePattern());
        return "index";
    }

    @GetMapping(value = "/list")
    public String list(@ModelAttribute() ListParameter parameter, Model model) {
        List<Drink> drinks = drinkService.getSearchAndFilterAcceptedDrinksResult(parameter, Status.ACCEPTED);
        model.addAttribute("listparameter", parameter);
        model.addAttribute("drinklist", drinks);
        model.addAttribute("dateformat", dateFormat.getDatePattern());
        return "drink_list";
    }
}
