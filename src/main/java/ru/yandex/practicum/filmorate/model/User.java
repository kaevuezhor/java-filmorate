package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User implements Identifiable{

    private final int id;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private final String login;
    private String nickname;
    private LocalDate birthdate;

}
