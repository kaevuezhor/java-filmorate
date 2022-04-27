package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmContollerTest {

    FilmController controller;

    @BeforeEach
    public void beforeEach() {
        controller = new FilmController();
    }

    @Test
    public void shouldGetFilms() throws ValidationException {
        Film film = new Film(
                1,
                "Киборг-убица",
                "Описание",
                LocalDate.of(1984,1,1),
                Duration.ofMinutes(108));
        controller.create(film);
        assertTrue(List.of(film).containsAll(controller.findAll().values()));
    }

    @Test
    public void shouldNotCreateFilmWithBlankTitle() {
        Film film = new Film(
                1,
                "",
                "Описание",
                LocalDate.of(1984,1,1),
                Duration.ofMinutes(108));
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
                Duration.ofMinutes(108));
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
                Duration.ofMinutes(108));
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
                Duration.ofMinutes(-108));
        assertThrows(ValidationException.class, () -> {
            controller.create(film);
        });
    }

}
