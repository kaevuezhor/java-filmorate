package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Optional;

public interface FilmStorage {

    HashMap<Integer, Film> findAll();

    Film create(Film film);

    Film update(Film film);

    void delete(int id);

    Optional<Film> find(int id);

    void addLike(int filmId, int userId);

    void removeLike(int filmId, int userId);
}
