package ru.maslenikov.firstspringapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.maslenikov.firstspringapp.models.User;
import ru.maslenikov.firstspringapp.security.MyUserDetails;
import ru.maslenikov.firstspringapp.services.UserService;

@Controller
@RequestMapping("")
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"", "/home", "/"})
    public String index(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        System.out.println(myUserDetails.getUser());
        return "home/index";
    }

}
