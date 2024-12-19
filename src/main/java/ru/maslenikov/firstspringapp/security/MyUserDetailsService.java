package ru.maslenikov.firstspringapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.maslenikov.firstspringapp.models.User;
import ru.maslenikov.firstspringapp.services.UserService;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

//    public MyUserDetailsService(UserService userService) {
//        this.userService = userService;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new MyUserDetails(userService.loadUserByName(username).orElseThrow(() -> new UsernameNotFoundException("пользователь " + username + " не найден")));
    }
}
