package ru.maslenikov.springjwttoken.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

// в этом классе генерируем токен, чтобы отдать клиенту, и здесь же валидируем, что пришлет клиент
@Component
@PropertySource("classpath:authentication.properties")
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    // то храним в JWT-токене
    private String generateToken(String username) {

        // срок хранения токена - от текущего момента 60 минут
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
                .withSubject("User details") // что вообще хранится в JWT-токене - данные пользователя
                .withClaim("username", username) // таких пар может быть хоть сколько, но пока зашиваем только username;
                .withIssuedAt(new Date()) // когда выдан этот токен
                .withIssuer("maslenikov") // кто выдал этот токен (в реальности, допустим, название приложения)
                .withExpiresAt(expirationDate) // когда срок годности иссякнет
                .sign(Algorithm.HMAC256(secret)); // подписываем токен, передав ему секрет и выбрав алгоритм шифрования
    }

    // теперь реализуем метод, проверяющий, что нам шлет клиент (валидация токена)
    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {

        //валидацию будет проходить только тот токен, у которого такой секрет, такое шифрование, такой subject и создатель
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("maslenikov")
                .build();

        // предоставляет декодированный jwt
        DecodedJWT jwt = verifier.verify(token);

        // если смогли расшифровать по параметрам выше, то достанем зашитый параметр
        return jwt.getClaim("username").asString();

    }

}
