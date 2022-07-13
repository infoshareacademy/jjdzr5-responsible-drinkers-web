package com.infoshareacademy.responsibledrinkersweb.controller;

import com.infoshareacademy.responsibledrinkersweb.domain.Gender;
import com.infoshareacademy.responsibledrinkersweb.domain.User;
import com.infoshareacademy.responsibledrinkersweb.dto.CreateUserDto;
import com.infoshareacademy.responsibledrinkersweb.service.DateFormat;
import com.infoshareacademy.responsibledrinkersweb.service.DrinkService;
import com.infoshareacademy.responsibledrinkersweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AnonymousController {

    private final DrinkService drinkService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

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
    public String newAccount(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/signup";
        }
        Gender gender;
        switch (user.getGender()) {
            case "MALE":
                gender = Gender.MALE;
                break;
            case "FEMALE":
                gender = Gender.FEMALE;
                break;
            default:
                gender = Gender.OTHER;
                break;
        }
        CreateUserDto createUserDto = new CreateUserDto(user.getUserName(), gender, user.getEmail(),
                passwordEncoder.encode(user.getPassword()), user.getDateOfBirth(), "REGISTERED", false);
        userService.addUser(createUserDto);
        model.addAttribute("userAccount", user);
        return "new_account";
    }

    @RequestMapping("/login")
    public String login(Model model, @RequestParam("error") final Optional<String> error,
                        @Value("${message.login.user.not_active}") String notActiveText,
                        @Value("${message.login.user.invalid}") String invalidCredentialsText) {
        error.ifPresent(s -> model.addAttribute("error", s));
        model.addAttribute("notActiveText", notActiveText);
        model.addAttribute("invalidCredentialsText", invalidCredentialsText);
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
}
