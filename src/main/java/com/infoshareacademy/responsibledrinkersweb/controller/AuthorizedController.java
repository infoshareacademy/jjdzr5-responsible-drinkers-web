package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.domain.drink.Status;
import com.infoshareacademy.drinkers.service.sorting.SortDrinks;
import com.infoshareacademy.drinkers.service.sorting.SortItems;
import com.infoshareacademy.responsibledrinkersweb.domain.ListParameter;
import com.infoshareacademy.responsibledrinkersweb.service.DateFormat;
import com.infoshareacademy.responsibledrinkersweb.service.DrinkService;
import com.infoshareacademy.responsibledrinkersweb.service.ParameterService;
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
//            drinkService.deleteDrink(drink.getId());
//            drinkService.addDrink(drink);
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
        List<Drink> drinkListManager;
        SortDrinks sortDrinks = new SortDrinks(drinkService.getDrinks());
        if (sort.equals("ID")) {
            drinkListManager = sortDrinks.getSortedList(SortItems.ID);
        } else if (sort.equalsIgnoreCase("NAME")) {
            drinkListManager = sortDrinks.getSortedList(SortItems.DRINK_NAME);
        } else if (sort.equalsIgnoreCase("TYPE")) {
            drinkListManager = sortDrinks.getSortedList(SortItems.ALCOHOLIC);
        } else if (sort.equalsIgnoreCase("DATE")) {
            drinkListManager = sortDrinks.getSortedList(SortItems.DATE);
        } else if (sort.equalsIgnoreCase("STATUS")) {
            drinkListManager = drinkService.getDrinks().stream()
                    .sorted((d1, d2) ->
                            d2.getStatus().getName().compareTo(d1.getStatus().getName())
                    ).toList();
        } else {
            drinkListManager = drinkService.getDrinks();
        }
        model.addAttribute("drinklist", drinkListManager);
        model.addAttribute("dateformat", dateFormat.getDatePattern());
        model.addAttribute("deleted", deleted);
        return "manager";
    }

    @GetMapping("edit")
    public String edit(@RequestParam UUID id, Model model) {
        Drink drink = drinkService.getDrink(id);
 //       drink.setId(id);
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
        System.out.println(parameter);

//        List<Drink> modifyList = drinkService.getDrinks();
//        if (parameter.getKeyword() != null) {
//            modifyList = drinkService.search(parameter.getKeyword());
//        }
//        if (parameter.getAlcoholic() != null) {
//            modifyList = modifyList.stream()
//                    .filter(drink -> drink.getAlcoholic().equalsIgnoreCase(parameter.getAlcoholic().getName()))
//                    .toList();
//        }
//        if (parameter.getFilterElements() != null) {
//            FilterList filterList = new FilterList(modifyList);
//            modifyList = filterList.getFilteredByIngredient(parameter.getFilterElements()).getResults();
//        }
//        if (parameter.getStatus() != null) {
//            FilterList filterList = new FilterList(modifyList);
//            modifyList = filterList.getFilteredByStatus(parameter.getStatus()).getResults();
//        }
//        SortDrinks sortDrinks = new SortDrinks(modifyList);
//        if (parameter.getSort() != null) {
//            modifyList = switch (parameter.getSort()) {
//                case "ID" -> sortDrinks.getSortedList(SortItems.ID);
//                case "NAME" -> sortDrinks.getSortedList(SortItems.DRINK_NAME);
//                case "TYPE" -> sortDrinks.getSortedList(SortItems.ALCOHOLIC);
//                case "DATE" -> sortDrinks.getSortedList(SortItems.DATE);
//                default -> modifyList;
//            };
//        }
        model.addAttribute("listparameter", parameter);
        model.addAttribute("drinklist", ParameterService.func(parameter, drinkService.getDrinks()));
        model.addAttribute("dateformat", dateFormat.getDatePattern());
        return "panel";
    }

    @GetMapping("/account_settings")
    public String account(Model model) {
        return "account_settings";
    }

}
