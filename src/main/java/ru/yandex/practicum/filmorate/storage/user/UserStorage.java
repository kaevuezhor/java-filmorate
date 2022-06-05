package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Optional;

public interface UserStorage {

    HashMap<Integer, User> findAll();

    User create(User user);

    User update(User user);

    void delete(int id);

    Optional<User> find(int id);
}
