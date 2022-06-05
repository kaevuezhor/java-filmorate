package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequestMapping("/films")
@RestController
@Slf4j
@RequiredArgsConstructor
public class FilmController{

    private final FilmService filmService;

    @GetMapping()
    public List<Film> findAll() {
        return filmService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Film> find(@PathVariable("id") Integer filmId) throws FilmNotFoundException {
        return filmService.find(filmId);
    }

    @PostMapping()
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        if (!validate(film)) {
            throw new ValidationException("Ошибка валидации");
        }
        return filmService.create(film);
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) throws ValidationException, FilmNotFoundException {
        if (!validate(film)) {
            throw new ValidationException("Ошибка валидации");
        }
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") Integer filmId,
                        @PathVariable("userId") Integer userId
    ) throws FilmNotFoundException, UserNotFoundException {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") Integer filmId,
                        @PathVariable("userId") Integer userId
    ) throws FilmNotFoundException, UserNotFoundException {
        filmService.removeLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(defaultValue = "10", required = false) Integer count) {
        return filmService.getPopular(count);
    }

    protected Boolean validate(Film film) {
        if (!StringUtils.hasText(film.getName())) {
            return false;
        }
        if (film.getDescription().length() > 200) {
            return false;
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            return false;
        }
        if (film.getDuration() < 0) {
            return false;
        }
        return true;
    }
}
