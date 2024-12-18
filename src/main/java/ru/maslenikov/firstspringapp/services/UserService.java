package ru.maslenikov.firstspringapp.services;

import org.springframework.stereotype.Service;
import ru.maslenikov.firstspringapp.models.User;
import ru.maslenikov.firstspringapp.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByName(String name) {
        return userRepository.findUserByName(name);
    }

}
