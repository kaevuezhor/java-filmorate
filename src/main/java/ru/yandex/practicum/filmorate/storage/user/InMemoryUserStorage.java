package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Optional;

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
    public Optional<User> find(int id) {
        return Optional.of(data.get(id));
    }

    @Override
    public void sendFriendRequest(int userId, int friendId) {
        data.get(userId).getFriends().add(friendId);
    }

    @Override
    public boolean hasFriendResponse(int userId, int friendId) {
        return true;
    }

    @Override
    public void confirmFriendRequest(int userId, int friendId) {
        data.get(friendId).getFriends().add(userId);
    }

    @Override
    public void declineFriendRequest(int userId, int friendId) {
        data.get(userId).getFriends().remove(friendId);
        data.get(friendId).getFriends().remove(userId);
    }
}
