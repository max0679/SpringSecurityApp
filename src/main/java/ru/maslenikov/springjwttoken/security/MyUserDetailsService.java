package ru.maslenikov.springjwttoken.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.maslenikov.springjwttoken.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    //@Autowired
    //private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new MyUserDetails(userRepository.findUserByName(username).orElseThrow(() -> new UsernameNotFoundException("пользователь " + username + " не найден")));
    }
}
