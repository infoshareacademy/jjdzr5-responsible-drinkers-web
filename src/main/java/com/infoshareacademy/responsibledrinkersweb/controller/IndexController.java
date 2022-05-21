package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.responsibledrinkersweb.domain.ListParameter;
import com.infoshareacademy.responsibledrinkersweb.service.DateFormat;
import com.infoshareacademy.responsibledrinkersweb.service.DrinkService;
import com.infoshareacademy.responsibledrinkersweb.service.ParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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
//        List<Drink> modifyList = drinkService.getAcceptedDrinks();
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
        model.addAttribute("drinklist", ParameterService.func(parameter, drinkService.getAcceptedDrinks()));
        model.addAttribute("dateformat", dateFormat.getDatePattern());
        return "drink_list";
    }

//    @GetMapping("/drink_list")
//    public String drinkList(Model model, @RequestParam(value = "sort", required = false, defaultValue = "0") int sort) {
//        List<Drink> drinkList = modifyList;
//        if (modifyList.isEmpty()) {
//            modifyList = drinkService.getDrinks();
//        }
//
//        SortDrinks sortDrinks = new SortDrinks(modifyList);
//        if (sort == 1) {
//            modifyList = sortDrinks.getSortedList(SortItems.ID);
//        } else if (sort == 2) {
//            modifyList = sortDrinks.getSortedList(SortItems.DRINK_NAME);
//        } else if (sort == 4) {
//            modifyList = sortDrinks.getSortedList(SortItems.ALCOHOLIC);
//        } else if (sort == 3) {
//            modifyList = sortDrinks.getSortedList(SortItems.DATE);
//        } else {
//            listParameter = new ListParameter();
//            modifyList = drinkService.getDrinks();
//        }
//        model.addAttribute("drinklist", modifyList);
//        model.addAttribute("dateformat", dateFormat.getDatePatter());
//        model.addAttribute("listparameter", listParameter);
//        return "drink_list";
//    }
}
