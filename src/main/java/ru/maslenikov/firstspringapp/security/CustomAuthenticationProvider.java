package ru.maslenikov.firstspringapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

// этот класс пригодится, если нужно будет использовать кастомную аутентификацию
// тогда в конфиг нужно будет прописать httpSecurity.authenticationProvider(customAuthenticationProvider);
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public CustomAuthenticationProvider(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        // Загружаем пользователя по имени
        UserDetails user = myUserDetailsService.loadUserByUsername(username);

        // Здесь вы можете добавить свою логику проверки пароля
        if (user != null && password.equals(user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        } else {
            throw new BadCredentialsException("Неверные учетные данные");
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
