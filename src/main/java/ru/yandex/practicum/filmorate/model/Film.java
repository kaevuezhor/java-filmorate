package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {

    private int id;
    //@NotBlank
    private String name;
    //@NotBlank
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Set<Integer> usersLiked;
    private int rate;

    public Film (int id, String name, String description, LocalDate releaseDate, int duration, int rate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
        usersLiked = new HashSet<>();
    }

}
