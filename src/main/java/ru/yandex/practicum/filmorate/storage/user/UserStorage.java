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

    void sendFriendRequest(int userId, int friendId);

    boolean hasFriendResponse(int userId, int friendId);

    void confirmFriendRequest(int userId, int friendId);

    void declineFriendRequest(int userId, int friendId);
}
