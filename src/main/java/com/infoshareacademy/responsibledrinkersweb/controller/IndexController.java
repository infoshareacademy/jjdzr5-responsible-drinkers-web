package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.domain.drink.Status;
import com.infoshareacademy.responsibledrinkersweb.domain.ListParameter;
import com.infoshareacademy.responsibledrinkersweb.service.DateFormat;
import com.infoshareacademy.responsibledrinkersweb.service.DrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
public class IndexController {

    private final DrinkService drinkService;
    private final DateFormat dateFormat;

    @GetMapping(value = "/list")
    public String list(@ModelAttribute() ListParameter parameter, Model model) {
        List<Drink> drinks = drinkService.getSearchAndFilterAcceptedDrinksResult(parameter, Status.ACCEPTED);
        model.addAttribute("listparameter", parameter);
        model.addAttribute("drinklist", drinks);
        model.addAttribute("dateformat", dateFormat.getDatePattern());
        return "drink_list";
    }


}
