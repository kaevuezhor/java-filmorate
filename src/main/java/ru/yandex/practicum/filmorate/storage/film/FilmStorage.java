package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;

public interface FilmStorage {

    HashMap<Integer, Film> findAll();

    void create(Film film);

    void update(Film film);

    void delete(int id);

    Film find(int id);
}
