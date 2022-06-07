package ru.yandex.practicum.filmorate.storage.user;

import java.util.List;

public interface UserFriendship {

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);

    List<Integer> getFriends(int userId);

    List<Integer> getCommonFriends(int userId, int otherId);
}
