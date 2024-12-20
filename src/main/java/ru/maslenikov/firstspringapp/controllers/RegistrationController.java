package ru.maslenikov.firstspringapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.maslenikov.firstspringapp.models.User;
import ru.maslenikov.firstspringapp.services.UserService;
import ru.maslenikov.firstspringapp.util.UserValidator;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public RegistrationController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/registration")
    public String registrationForm(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "registration";

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "registration";

        userService.saveUser(user);

        return "redirect:/home";
    }

}
