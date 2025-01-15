package ru.maslenikov.springjwttoken.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.maslenikov.springjwttoken.exceptions.CustomAuthenticationFailureHandler;
import ru.maslenikov.springjwttoken.security.CustomAuthenticationProvider;
import ru.maslenikov.springjwttoken.security.MyUserDetailsService;

@Configuration
@EnableWebSecurity // конфигурационный класс SpringSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        //provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        // сейчас аутентификация происходит автоматически. однако если нужно написать кастомную аутентификацию, то
        // нужно использовать authenticationProvider

        // закомментировать эту строку, если хотим использовать стандартную аутентификацию
        httpSecurity.authenticationProvider(new CustomAuthenticationProvider(this));

        httpSecurity
                //.csrf(AbstractHttpConfigurer::disable) // так как теперь отправляем запрос через Postman
                .authorizeHttpRequests(auth -> auth
                       // .requestMatchers("**").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/login", "/registration").not().authenticated()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureHandler(new CustomAuthenticationFailureHandler()) // устанавливаем кастомный обработчик
                        )
                .csrf().disable()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // чтобы SpringSecurity больше не сохранял сессию на сервере

        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

       return httpSecurity.build();
    }

    //@Bean
    //AuthenticationManager authenticationManagerBean() {
       // return super.
    //}

}
