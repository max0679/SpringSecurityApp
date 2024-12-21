package ru.maslenikov.firstspringapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.maslenikov.firstspringapp.config.SecurityConfig;

// этот класс пригодится, если нужно будет использовать кастомную аутентификацию
// тогда в конфиг нужно будет прописать httpSecurity.authenticationProvider(customAuthenticationProvider);
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final SecurityConfig securityConfig;

    public CustomAuthenticationProvider(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = securityConfig.passwordEncoder().encode((String) authentication.getCredentials());

        // Загружаем пользователя по имени
        UserDetails user = securityConfig.userDetailsService().loadUserByUsername(username);

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
