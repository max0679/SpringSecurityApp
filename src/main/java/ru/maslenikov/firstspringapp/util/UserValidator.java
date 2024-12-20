package ru.maslenikov.firstspringapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maslenikov.firstspringapp.models.User;
import ru.maslenikov.firstspringapp.services.UserService;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;

        if (userService.loadUserByName(((User) target).getName()).isPresent()) {
            errors.rejectValue("name", "500", "пользователь с таким логином уже существует");
        }


    }
}
