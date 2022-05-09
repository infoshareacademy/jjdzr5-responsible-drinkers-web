package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.domain.drink.Status;
import com.infoshareacademy.drinkers.service.filtering.FilterList;
import com.infoshareacademy.drinkers.service.sorting.SortDrinks;
import com.infoshareacademy.drinkers.service.sorting.SortItems;
import com.infoshareacademy.responsibledrinkersweb.domain.ListParameter;
import com.infoshareacademy.responsibledrinkersweb.domain.User;
import com.infoshareacademy.responsibledrinkersweb.service.DateFormat;
import com.infoshareacademy.responsibledrinkersweb.service.DrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
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
        model.addAttribute("drinklist", func(parameter,drinkService.getAcceptedDrinks()));
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
            drink.setStatus(Status.ADDED);
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
            drinkService.deleteDrink(drink.getIdDrink());
            drinkService.addDrink(drink);
            model.addAttribute("drink", drink);
            model.addAttribute("dateformat", dateFormat.getDatePattern());
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
        return new RedirectView("/manager");
    }

    @GetMapping("/manager")
    public String manager(Model model, @RequestParam(value = "sort", required = false, defaultValue = "0") String sort) {
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
        return "manager";
    }

    private List<Drink> func(ListParameter parameter, List<Drink> drinkList) {
        List<Drink> modifyList = drinkList;
        if (parameter.getKeyword() != null) {
            modifyList = drinkService.search(parameter.getKeyword());
        }
        if (parameter.getAlcoholic() != null) {
            modifyList = modifyList.stream()
                    .filter(drink -> drink.getAlcoholic().equalsIgnoreCase(parameter.getAlcoholic().getName()))
                    .toList();
        }
        if (parameter.getFilterElements() != null) {
            FilterList filterList = new FilterList(modifyList);
            modifyList = filterList.getFilteredByIngredient(parameter.getFilterElements()).getResults();
        }
        if (parameter.getStatus() != null) {
            FilterList filterList = new FilterList(modifyList);
            modifyList = filterList.getFilteredByStatus(parameter.getStatus()).getResults();
        }
        SortDrinks sortDrinks = new SortDrinks(modifyList);
        if (parameter.getSort() != null) {
            modifyList = switch (parameter.getSort()) {
                case "ID" -> sortDrinks.getSortedList(SortItems.ID);
                case "NAME" -> sortDrinks.getSortedList(SortItems.DRINK_NAME);
                case "TYPE" -> sortDrinks.getSortedList(SortItems.ALCOHOLIC);
                case "DATE" -> sortDrinks.getSortedList(SortItems.DATE);
                default -> modifyList;
            };
        }
        return modifyList;
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
        model.addAttribute("drinklist", func(parameter, drinkService.getDrinks()));
        model.addAttribute("dateformat", dateFormat.getDatePattern());
        return "panel";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/new_account")
    public String newAccount(@Valid @ModelAttribute User account, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/signup";
        }
        model.addAttribute("userAccount", account);
        return "new_account";
    }


    @GetMapping("/account_settings")
    public String account(Model model) {
        return "account_settings";
    }


    @RequestMapping("/show-more")
    public String showMore(@RequestParam int id, Model model) {
        model.addAttribute("drink", drinkService.getDrink(id));
        model.addAttribute("dateformat", dateFormat.getDatePattern());
        return "show-more";
    }
}
