package ru.maslenikov.springjwttoken.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.maslenikov.springjwttoken.security.JWTUtil;
import ru.maslenikov.springjwttoken.security.MyUserDetails;
import ru.maslenikov.springjwttoken.security.MyUserDetailsService;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final MyUserDetailsService myUserDetailsService;

    public JWTFilter(JWTUtil jwtUtil, MyUserDetailsService myUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (token.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT-token in Bearer header");
            } else {
                try {
                    // если не было выброшено исключение
                    String username = jwtUtil.validateTokenAndRetrieveClaim(token);
                    MyUserDetails userDetails = (MyUserDetails) myUserDetailsService.loadUserByUsername(username);

                    // аутентифицируем пользователя
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } catch (JWTVerificationException exception) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT-token in Bearer header 2");
                }

            }
        }

        // продвигаем далее наш запрос после аутентификации
        filterChain.doFilter(request, response);
    }
}
