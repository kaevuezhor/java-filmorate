package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.Duration;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film implements Identifiable{

    private final int id;
    //@NotBlank
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Duration duration;

}
