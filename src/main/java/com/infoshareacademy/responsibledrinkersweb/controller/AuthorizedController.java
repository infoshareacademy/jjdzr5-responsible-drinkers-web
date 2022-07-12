package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.drinkers.domain.drink.Drink;
import com.infoshareacademy.drinkers.domain.drink.Status;
import com.infoshareacademy.responsibledrinkersweb.domain.Count;
import com.infoshareacademy.responsibledrinkersweb.domain.ListParameter;
import com.infoshareacademy.responsibledrinkersweb.dto.UserDto;
import com.infoshareacademy.responsibledrinkersweb.entity.DrinkDAO;
import com.infoshareacademy.responsibledrinkersweb.entity.UserDAO;
import com.infoshareacademy.responsibledrinkersweb.mapper.DrinkMapper;
import com.infoshareacademy.responsibledrinkersweb.mapper.UserMapper;
import com.infoshareacademy.responsibledrinkersweb.service.DateFormat;
import com.infoshareacademy.responsibledrinkersweb.service.DrinkService;
import com.infoshareacademy.responsibledrinkersweb.service.UserService;
import com.infoshareacademy.responsibledrinkersweb.service.http.SendRequestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Secured(value = {"ROLE_USER", "ROLE_ADMIN","ROLE_REGISTERED"})
public class AuthorizedController {

    private final DrinkService drinkService;
    private final DateFormat dateFormat;
    private final UserService userService;
    private final UserMapper userMapper;
    private final DrinkMapper drinkMapper;

    private final SendRequestService sendRequestService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizedController.class);

    @GetMapping(value = "/list")
    public String list(@ModelAttribute() ListParameter parameter, Model model) {
        List<Drink> drinks = drinkService.getSearchAndFilterAcceptedDrinksResult(parameter, Status.ACCEPTED);
        if (parameter.getKeyword() != null && parameter.getKeyword().length() > 0) {
            sendRequestService.sendPostRequest(parameter);
        }
        model.addAttribute("listparameter", parameter);
        model.addAttribute("drinklist", drinks);
        model.addAttribute("dateformat", dateFormat.getDatePattern());
        return "drink_list";
    }

    @PostMapping("/new_drink")
    public String newDrink(@Valid @ModelAttribute Drink drink, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_new_drink";
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            UserDto currentUserDto = userService.findByUserName(currentPrincipalName);
            UserDAO currentUserDAO = userMapper.mapToUserDAO(currentUserDto);
            DrinkDAO drinkDAO = drinkMapper.mapDinkToDrinkDAO(drink);
            drinkDAO.setUserDAO(currentUserDAO);
            Drink drinkWithUser = drinkMapper.mapDrinkDAOToDrink(drinkDAO);

            drinkService.addDrink(drinkWithUser);
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
        if (parameter.getKeyword() != null && parameter.getKeyword().length() > 0) {
            sendRequestService.sendPostRequest(parameter);
        }
        model.addAttribute("listparameter", parameter);
        model.addAttribute("drinklist", searchAndFilterResult);
        model.addAttribute("dateformat", dateFormat.getDatePattern());
        return "panel";
    }

    @GetMapping("/account_settings")
    public String account(Model model) {
        return "account_settings";
    }

    @GetMapping("/stats")
    @Secured("ROLE_ADMIN")
    public String stats(Model model) {
        List<Count> counts = sendRequestService.sendPGetRequest();
        String jsCode = getJSCode(counts);
        model.addAttribute("counts", counts);
        model.addAttribute("scriptcode", jsCode);
        return "stats";
    }

    private String getJSCode(List<Count> counts) {
        StringBuilder code = new StringBuilder();
        code.append("google.visualization.arrayToDataTable([")
                .append("['Word', 'Counts'],").append(System.lineSeparator());
        for (Count c : counts) {
            code.append("['").append(c.getWord()).append("', ")
                    .append(c.getCounts()).append("]");
            code.append(",").append(System.lineSeparator());
        }
        code.setLength(code.length() - 2);
        code.append("]);");
        return code.toString();
    }
}
