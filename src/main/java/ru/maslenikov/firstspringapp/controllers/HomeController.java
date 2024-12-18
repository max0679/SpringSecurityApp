package ru.maslenikov.firstspringapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.maslenikov.firstspringapp.models.User;
import ru.maslenikov.firstspringapp.services.UserService;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String index() {
        User user = userService.getUserByName("user2");
        return "home/index";
    }

}
