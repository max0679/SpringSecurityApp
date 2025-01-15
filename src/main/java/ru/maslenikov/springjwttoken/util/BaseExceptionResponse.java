package ru.maslenikov.springjwttoken.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseExceptionResponse {

    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    public BaseExceptionResponse(String message) {
        this.message = message;
        this.localDateTime = LocalDateTime.now();
    }

}
