package ru.maslenikov.springjwttoken.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import ru.maslenikov.springjwttoken.models.User;
import ru.maslenikov.springjwttoken.repositories.UserRepository;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    //private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public Optional<User> loadUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    public void saveUser(User user) {
        user.setRoles(Collections.singleton(roleService.getRoleByName("ROLE_USER")));
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
