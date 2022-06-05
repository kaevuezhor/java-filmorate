package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserFriendship;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;
    private final UserFriendship userFriendship;

    @Autowired
    public UserService(UserStorage userStorage, UserFriendship userFriendship) {
        this.userStorage = userStorage;
        this.userFriendship = userFriendship;
    }

    public List<User> findAll() { return new ArrayList<>(userStorage.findAll().values()); }

    public Optional<User> find(int id) throws UserNotFoundException {
        if (!isExistingUser(id)) {
            throw new UserNotFoundException(String.format(
                    "Пользователь с id %s не найден",
                    id));
        }
        return userStorage.find(id);
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) throws UserNotFoundException {
        if (!isExistingUser(user.getId())) {
            throw new UserNotFoundException(String.format(
                    "Пользователь с id %s не найден",
                    user.getId()));
        }
        return userStorage.update(user);
    }

    public void addFriend(int userId, int friendId) throws UserNotFoundException {
        if (!isExistingUser(userId)) {
            throw new UserNotFoundException(String.format(
                    "Пользователь с id %s не найден",
                    userId));
        }
        if (!isExistingUser(friendId)) {
            throw new UserNotFoundException(String.format(
                    "Пользователь с id %s не найден",
                    friendId));
        }
        userFriendship.addFriend(userId, friendId);
    }



    public void deleteFriend(int userId, int friendId) throws UserNotFoundException {
        if (!isExistingUser(userId)) {
            throw new UserNotFoundException(String.format(
                    "Пользователь с id %s не найден",
                    userId));
        }
        if (!isExistingUser(friendId)) {
            throw new UserNotFoundException(String.format(
                    "Пользователь с id %s не найден",
                    friendId));
        }
        userFriendship.deleteFriend(userId, friendId);
    }



    public List<User> getFriends(int userId) throws UserNotFoundException {
        if (!isExistingUser(userId)) {
            throw new UserNotFoundException(String.format(
                    "Пользователь с id %s не найден",
                    userId));
        }
        return userFriendship.getFriends(userId).stream()
                .map(id -> {
                    try {
                        return find(id);
                    } catch (UserNotFoundException e) {
                        e.printStackTrace();
                        return Optional.<User>empty();
                    }
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }



    public List<User> getCommonFriends(int userId, int otherId) throws UserNotFoundException {
        if (!isExistingUser(userId)) {
            throw new UserNotFoundException(String.format(
                    "Пользователь с id %s не найден",
                    userId));
        }
        if (!isExistingUser(otherId)) {
            throw new UserNotFoundException(String.format(
                    "Пользователь с id %s не найден",
                    otherId));
        }
        return userFriendship.getCommonFriends(userId, otherId).stream()
                .map(id -> {
                    try {
                        return find(id);
                    } catch (UserNotFoundException e) {
                        e.printStackTrace();
                        return Optional.<User>empty();
                    }
                }).filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }


    public boolean isExistingUser(int id) {
        return userStorage.findAll().containsKey(id);
    }
}
