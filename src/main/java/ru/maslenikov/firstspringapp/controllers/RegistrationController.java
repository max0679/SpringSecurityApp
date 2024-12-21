package ru.maslenikov.firstspringapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserService userService, UserValidator userValidator, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String registrationForm(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(HttpServletRequest request, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "registration";

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "registration";

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.saveUser(user);
        userService.authenticateUser(request, user); //не работает!!!

        return "redirect:/home";
    }

}
