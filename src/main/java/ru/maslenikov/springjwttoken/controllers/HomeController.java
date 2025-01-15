package ru.maslenikov.springjwttoken.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.maslenikov.springjwttoken.security.MyUserDetails;
import ru.maslenikov.springjwttoken.services.UserService;

@Controller
@RequestMapping("")
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"", "/home", "/"})
    @ResponseBody
    public String index(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        System.out.println(myUserDetails.getUser());
        return myUserDetails.getUsername() + " " + myUserDetails.getPassword() + " " + authentication.getCredentials();
    }

}
