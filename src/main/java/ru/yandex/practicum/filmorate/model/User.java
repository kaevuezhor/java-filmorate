package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {

    private int id;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private final String login;
    private String name;
    private LocalDate birthday;
    private Set<Integer> friends;

}
