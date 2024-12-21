package ru.maslenikov.firstspringapp.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.maslenikov.firstspringapp.models.User;
import ru.maslenikov.firstspringapp.repositories.UserRepository;
import ru.maslenikov.firstspringapp.security.MyUserDetails;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    //private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> loadUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    // после регистрации сразу аутентифицируем пользователя
    public void authenticateUser(HttpServletRequest request, User user) {

//        request.getSession();
//
//        MyUserDetails myUserDetails = new MyUserDetails(user);
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(myUserDetails, myUserDetails.getPassword()); //Важно: пароль уже захеширован в базе данных
//        Authentication authentication = authenticationManager.authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

}
