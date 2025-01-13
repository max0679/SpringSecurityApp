package ru.maslenikov.springjwttoken.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maslenikov.springjwttoken.dto.UserDTO;
import ru.maslenikov.springjwttoken.exceptions.RegistrationException;
import ru.maslenikov.springjwttoken.mappers.UserMapper;
import ru.maslenikov.springjwttoken.models.User;
import ru.maslenikov.springjwttoken.security.JWTUtil;
import ru.maslenikov.springjwttoken.services.UserService;
import ru.maslenikov.springjwttoken.util.UserValidator;

@RestController
public class RegistrationController extends BaseController{

    private final UserService userService;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Autowired
    public RegistrationController(UserService userService, UserValidator userValidator, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/registration")
    public String registrationForm(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(HttpServletRequest request, @RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            throw new RegistrationException("ошибка при вводе данных");

        User user = UserMapper.userDtoToUser(userDTO);

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            throw new RegistrationException("ошибка при вводе данных");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.saveUser(user);
        userService.authenticateUser(request, user); //не работает!!!

        return new ResponseEntity<>("данные сохранены", HttpStatus.OK);
    }

}
