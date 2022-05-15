package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmContollerTest {

    FilmController controller;
    FilmService service;
    FilmStorage filmStorage;
    UserStorage userStorage;

    @BeforeEach
    public void beforeEach() {
        filmStorage = new InMemoryFilmStorage();
        service = new FilmService(filmStorage, userStorage);
        controller = new FilmController(service);
    }

    @Test
    public void shouldGetFilms() throws ValidationException {
        Film film = new Film(
                1,
                "Киборг-убица",
                "Описание",
                LocalDate.of(1984,1,1),
                108,
                0);
        controller.create(film);
        assertTrue(List.of(film).containsAll(controller.findAll()));
    }

    @Test
    public void shouldNotCreateFilmWithBlankTitle() {
        Film film = new Film(
                1,
                "",
                "Описание",
                LocalDate.of(1984,1,1),
                108,
                0);
        assertThrows(ValidationException.class, () -> {
            controller.create(film);
        });
    }

    @Test
    public void shouldNotCreateFilmWithTooLongDescription() {
        Film film = new Film(
                1,
                "Киборг-убица",
                "Описаниеееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееее" +
                        "еееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееее" +
                        "еееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееее",
                LocalDate.of(1984,1,1),
                108,
                1);
        assertThrows(ValidationException.class, () -> {
            controller.create(film);
        });
    }

    @Test
    public void shouldNotCreateFilmWithWrongReleaseDate() {
        Film film = new Film(
                1,
                "Киборг-убица",
                "Описание",
                LocalDate.of(1888,8,8),
                108,
                1);
        assertThrows(ValidationException.class, () -> {
            controller.create(film);
        });
    }

    @Test
    public void shouldNotCreateFilmWithNegativeDuration() {
        Film film = new Film(
                1,
                "Киборг-убица",
                "Описание",
                LocalDate.of(1984,1,1),
                -108,
                1);
        assertThrows(ValidationException.class, () -> {
            controller.create(film);
        });
    }

}
