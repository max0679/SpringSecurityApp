package ru.maslenikov.springjwttoken.util;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class BaseExceptionResponse {

    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    public BaseExceptionResponse(String message) {
        this.message = message;
        this.localDateTime = LocalDateTime.now();
    }

}
