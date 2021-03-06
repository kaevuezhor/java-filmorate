package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public List<Film> findAll() {
        return new ArrayList<>(filmStorage.findAll().values());
    }

    public Optional<Film> find(int filmId) throws FilmNotFoundException {
        if (!isExistingFilm(filmId)) {
            throw new FilmNotFoundException(String.format(
                    "Фильм с id %s не найден",
                    filmId));
        }

        return filmStorage.find(filmId);
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) throws FilmNotFoundException {
        if (!isExistingFilm(film.getId())) {
            throw new FilmNotFoundException(String.format(
                    "Фильм с id %s не найден",
                    film.getId()));
        }
        filmStorage.update(film);
        return filmStorage.find(film.getId()).get();
    }

    public boolean isExistingFilm(int filmId) {
        return filmStorage.findAll().containsKey(filmId);
    }

    public void addLike(int filmId, int userId) throws FilmNotFoundException, UserNotFoundException {
        if (!isExistingFilm(filmId)) {
            throw new FilmNotFoundException(String.format(
                    "Фильм с id %s не найден",
                    filmId));
        }
        if (!userStorage.findAll().containsKey(userId)) {
            throw new UserNotFoundException(String.format(
                    "Пользователь с id %s не найден",
                    userId));
        }
        filmStorage.find(filmId).stream()
                .map(Film::getUsersLiked)
                .forEach(s -> s.add(userId));
        filmStorage.addLike(filmId, userId);
    }

    public void removeLike(int filmId, int userId) throws FilmNotFoundException, UserNotFoundException {
        if (!isExistingFilm(filmId)) {
            throw new FilmNotFoundException(String.format(
                    "Фильм с id %s не найден",
                    filmId));
        }
        if (!userStorage.findAll().containsKey(userId)) {
            throw new UserNotFoundException(String.format(
                    "Пользователь с id %s не найден",
                    userId));
        }
        filmStorage.removeLike(filmId, userId);
    }

    public List<Film> getPopular(int count) {
        return filmStorage.findAll().values().stream()
                .sorted(Comparator.comparingInt(f0 -> -1 * f0.getUsersLiked().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
