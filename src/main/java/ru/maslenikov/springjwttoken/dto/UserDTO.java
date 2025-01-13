package ru.maslenikov.springjwttoken.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    @NotNull(message = "поле обязательно к сохранению")
    private String name;

    @Min(value = 1900, message = "минимальный год - 1900")
    private int year;

    @NotNull(message = "поле обязательно к сохранению")
    private String password;

}
