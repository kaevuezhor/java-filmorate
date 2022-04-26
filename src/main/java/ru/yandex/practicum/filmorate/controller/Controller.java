package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Identifiable;

import javax.validation.Valid;
import java.util.HashMap;

public abstract class Controller<T extends Identifiable> {

    private HashMap<Integer, T> data = new HashMap<>();

    @GetMapping()
    public HashMap<Integer, T> findAll() {
        return data;
    }

    @PostMapping()
    public void create(@Valid @RequestBody T t) throws ValidateException {
        if (!validate(t)) {
            throw new ValidateException("Ошибка валидации");
        }
        int id = data.size() + 1;
        data.put(id, convert(id, t));
    }

    @PutMapping()
    public void update(@Valid @RequestBody T t) {
        if (!validate(t) || !data.containsKey(t.getId())) {
            throw new ValidateException("Ошибка валидации");
        }
        data.put(t.getId(), t);
    }

    protected abstract Boolean validate(T t);

    protected abstract T convert(int id, T t);

}
