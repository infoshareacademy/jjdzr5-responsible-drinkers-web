package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.responsibledrinkersweb.domain.ListParameter;
import com.infoshareacademy.responsibledrinkersweb.service.DateFormat;
import com.infoshareacademy.responsibledrinkersweb.service.DrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthorizedController {

    private final DrinkService drinkService;
    private final DateFormat dateFormat;

    @PostMapping("/new_drink")
    public String newDrink(@Valid @ModelAttribute Drink drink, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_new_drink";
        } else {
            drinkService.addDrink(drink);
            model.addAttribute("dateformat", dateFormat.getDatePattern());
            model.addAttribute("drink", drink);
            return "new_drink";
        }
    }

    @PostMapping("/modify")
    public String modify(@Valid @ModelAttribute Drink drink, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "edit";
        } else {
            drinkService.update(drink);
            model.addAttribute("drink", drink);
            model.addAttribute("dateformat", dateFormat.getDatePattern());
            return "modify";
        }
    }

    @GetMapping("delete-drink")
    public RedirectView delete(@RequestParam UUID id) {
        drinkService.deleteDrink(id);
        return new RedirectView("/manager?deleted=true");
    }

    @GetMapping("/manager")
    public String manager(Model model, @RequestParam(value = "sort", required = false, defaultValue = "0") String sort, @RequestParam(value = "deleted", required = false, defaultValue = "false") boolean deleted) {
        ListParameter listParameter = new ListParameter();
        listParameter.setSort(sort);
        List<Drink> searchAndFilterResult = drinkService.getSearchAndFilterAllDrinksResult(listParameter);
        model.addAttribute("drinklist", searchAndFilterResult);
        model.addAttribute("dateformat", dateFormat.getDatePattern());
        model.addAttribute("deleted", deleted);
        return "manager";
    }

    @GetMapping("edit")
    public String edit(@RequestParam UUID id, Model model) {
        Drink drink = drinkService.getDrink(id);
        model.addAttribute("drink", drink);
        return "edit";
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

    @GetMapping("/panel")
    public String panel(@ModelAttribute ListParameter parameter, Model model) {
        List<Drink> searchAndFilterResult = drinkService.getSearchAndFilterAllDrinksResult(parameter);
        model.addAttribute("listparameter", parameter);
        model.addAttribute("drinklist", searchAndFilterResult);
        model.addAttribute("dateformat", dateFormat.getDatePattern());
        return "panel";
    }

    @GetMapping("/account_settings")
    public String account(Model model) {
        return "account_settings";
    }

}
