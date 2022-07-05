package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.responsibledrinkersweb.domain.User;
import com.infoshareacademy.responsibledrinkersweb.dto.UserDto;
import com.infoshareacademy.responsibledrinkersweb.service.DateFormat;
import com.infoshareacademy.responsibledrinkersweb.service.DrinkService;
import com.infoshareacademy.responsibledrinkersweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AnonymousController {

    private final DrinkService drinkService;
    private final UserService userService;

    private final DateFormat dateFormat;
    private static final Integer ELEMENTS_TO_PRINT = 8;

    @GetMapping(path = {"/", "/index"})
    public String main(Model model) {
        model.addAttribute("newestdrinks", drinkService.getNewestAccepted(ELEMENTS_TO_PRINT));
        model.addAttribute("dateformat", dateFormat.getDatePattern());
        return "index";
    }

    @RequestMapping("/show-more")
    public String showMore(@RequestParam UUID id, Model model) {
        model.addAttribute("drink", drinkService.getDrink(id));
        model.addAttribute("dateformat", dateFormat.getDatePattern());
        return "show-more";
    }

    @PostMapping("/new_account")
    public String newAccount(@Valid @ModelAttribute User account, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/signup";
        }
        model.addAttribute("userAccount", account);
        return "new_account";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/block/{id}")
    public String blockUser(@PathVariable UUID id) {
        userService.changeUserIsActiveFlag(id);
        return "redirect:/users";
    }

}
