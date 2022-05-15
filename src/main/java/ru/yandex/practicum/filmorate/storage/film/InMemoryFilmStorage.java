package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    HashMap<Integer, Film> data = new HashMap<>();
    int counter = 1;

    @Override
    public HashMap<Integer, Film> findAll() {
        return data;
    }

    @Override
    public void create(Film film) {
        film.setId(counter++);
        data.put(film.getId(), film);
    }

    @Override
    public void update(Film film) {
        data.put(film.getId(), film);
    }

    @Override
    public void delete(int id) {
        data.remove(id);
    }

    @Override
    public Film find(int id) {
        return data.get(id);
    }
}
