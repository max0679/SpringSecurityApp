package ru.maslenikov.springjwttoken.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseException extends RuntimeException{

    protected LocalDateTime createdAt;
    protected String message;

    public BaseException(String message) {
        createdAt = LocalDateTime.now();
        this.message = message;
    }

}
