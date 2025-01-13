package ru.maslenikov.springjwttoken.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    @NotNull(message = "поле обязательно к сохранению")
    private String name;

    @Column(name = "year_of_birth")
    @Min(value = 1900, message = "минимальный год - 1900")
    private int year;

    @Column(name = "password")
    @NotNull(message = "поле обязательно к сохранению")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role> roles;

    public User(String name, int year) {
        this.name = name;
        this.year = year;
    }

}
