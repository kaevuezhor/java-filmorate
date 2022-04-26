package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@RequestMapping("/films")
@RestController
@Slf4j
public class FilmController extends Controller<Film>{

    @Override
    protected Boolean validate(Film film) {
        if (film.getTitle().isBlank()) {
            return false;
        }
        if (film.getDescription().length() > 200) {
            return false;
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            return false;
        }
        if (film.getDuration().isNegative()) {
            return false;
        }
        return true;
    }

    @Override
    protected Film convert(int id, Film film) {
        return new Film(
                id,
                film.getTitle(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration()
        );
    }

}
