package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.responsibledrinkersweb.domain.User;
import com.infoshareacademy.responsibledrinkersweb.service.DateFormat;
import com.infoshareacademy.responsibledrinkersweb.service.DrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequiredArgsConstructor

public class AnonymousController {

    private final DrinkService drinkService;
    private final DateFormat dateFormat;

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

    @RequestMapping("/show-more")
    public String showMore(@RequestParam UUID id, Model model) {
        model.addAttribute("drink", drinkService.getDrink(id));
        model.addAttribute("dateformat", dateFormat.getDatePattern());
        return "show-more";
    }
}
