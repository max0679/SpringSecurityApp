package ru.maslenikov.firstspringapp.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.maslenikov.firstspringapp.models.User;
import ru.maslenikov.firstspringapp.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> loadUserByName(String name) {
        return userRepository.findUserByName(name);
    }

}
