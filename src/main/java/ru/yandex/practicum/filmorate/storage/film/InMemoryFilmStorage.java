package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Optional;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    HashMap<Integer, Film> data = new HashMap<>();
    int counter = 1;

    @Override
    public HashMap<Integer, Film> findAll() {
        return data;
    }

    @Override
    public Film create(Film film) {
        film.setId(counter++);
        data.put(film.getId(), film);
        return data.get(film.getId());
    }

    @Override
    public Film update(Film film) {
        data.put(film.getId(), film);
        return data.get(film.getId());
    }

    @Override
    public void delete(int id) {
        data.remove(id);
    }

    @Override
    public Optional<Film> find(int id) {
        return Optional.of(data.get(id));
    }

    @Override
    public void addLike(int filmId, int userId) {
        data.get(filmId).getUsersLiked().add(userId);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        data.get(filmId).getUsersLiked().remove(userId);
    }
}
