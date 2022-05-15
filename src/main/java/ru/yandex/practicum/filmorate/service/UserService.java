package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> findAll() { return userStorage.findAll().values().stream().collect(Collectors.toList()); }

    public User find(int id) throws UserNotFoundException {
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
        userStorage.find(userId)
                .getFriends()
                .add(friendId);
        userStorage.find(friendId)
                .getFriends()
                .add(userId);
    }

    public void deleteFriend(int userId, int friendId) {
        userStorage.find(userId)
                .getFriends()
                .remove(userStorage.find(friendId));
        userStorage.find(friendId)
                .getFriends()
                .remove(userStorage.find(userId));
    }

    public List<User> getFriends(int userId) {
        return userStorage.find(userId).getFriends().stream()
                .map(u -> userStorage.find(u))
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(int userId, int otherId) {
        Set<Integer> commonFriends = new HashSet<>(userStorage.find(userId).getFriends());
        commonFriends.retainAll(userStorage.find(otherId).getFriends());
        return commonFriends.stream()
                .map(u -> userStorage.find(u))
                .collect(Collectors.toList());
    }

    public boolean isExistingUser(int id) {
        return userStorage.findAll().containsKey(id);
    }

}
