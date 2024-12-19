package ru.maslenikov.firstspringapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.maslenikov.firstspringapp.exceptions.CustomAuthenticationFailureHandler;
import ru.maslenikov.firstspringapp.security.MyUserDetailsService;

@Configuration
@EnableWebSecurity // конфигурационный класс SprintSecurity
@EnableMethodSecurity // прочитать
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); // потом вернуть BCrypt
        //provider.setPasswordEncoder(passwordEncoder()); // сейчас из-за этого, наверное, не выйдет
        return provider;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                       // .requestMatchers("**").permitAll()
                        .requestMatchers("**").authenticated())
                .formLogin(form -> form
                        .loginPage("/login") // указываем свою страницу входа
                        .failureHandler(new CustomAuthenticationFailureHandler()) // Устанавливаем кастомный обработчик
                        .permitAll()) // разрешаем всем доступ к форме входа)
                //.formLogin(AbstractAuthenticationFilterConfigurer::permitAll)

                .build();
    }

}
