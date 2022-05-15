package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;

@Component
public class InMemoryUserStorage implements UserStorage {

    HashMap<Integer, User> data = new HashMap<>();
    int counter = 1;

    @Override
    public HashMap<Integer, User> findAll() {
        return data;
    }

    @Override
    public User create(User user) {
        user.setId(counter++);
        data.put(user.getId(), user);
        return data.get(user.getId());
    }

    @Override
    public User update(User user) {
        data.put(user.getId(), user);
        return data.get(user.getId());
    }

    @Override
    public void delete(int id) {
        data.remove(id);
    }

    @Override
    public User find(int id) {
        return data.get(id);
    }
}
