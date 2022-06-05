package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserFriendshipOnRequest implements UserFriendship {

    private final JdbcTemplate jdbcTemplate;

    public UserFriendshipOnRequest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(int userId, int friendId) {
        if (userId == friendId) {
            return;
        }
        String sql = "INSERT INTO user_friend (user_id, friend_id)" +
                "VALUES (?, ?);" ;
        jdbcTemplate.update(sql,
                userId,
                friendId
        );
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        if (userId == friendId) {
            return;
        }
        String sql = "DELETE FROM user_friend WHERE user_id = ? AND friend_id = ?;";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public List<Integer> getFriends(int userId) {
        String sql = "SELECT * FROM user_friend WHERE user_id = ? ;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFriendship(rs), userId).stream()
                .map(Friendship::getFriendId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Integer> getCommonFriends(int userId, int otherId) {
        Set<Integer> commonFriends = new HashSet<>(getFriends(userId));
        commonFriends.retainAll(getFriends(otherId));
        return new ArrayList<>(commonFriends);
    }

    private Friendship makeFriendship(ResultSet rs) throws SQLException {
        return new Friendship(rs.getInt("user_id"), rs.getInt("friend_id"));
    }














    /*
    

    @Override
    public void sendFriendRequest(int userId, int friendId) {
        if (userId == friendId) {
            return;
        }
        String sql = "INSERT INTO user_friend (user_id, friend_id, status)" +
                "VALUES (?, ?);" ;
        jdbcTemplate.update(sql,
                userId,
                friendId
        );
    }

    @Override
    public boolean hasFriendResponse(int userId, int friendId) {
        SqlRowSet sqlRows = jdbcTemplate.queryForRowSet(
                "SELECT status FROM user_friend WHERE user_id = ? AND friend_Id = ?;",
                friendId,
                userId
        );
        if (sqlRows.next()) {
            return true;
        }
        return false;
    }

    @Override
    public void confirmFriendRequest(int userId, int friendId) {
        String sql = "UPDATE user_friend SET " +
                "status = 'ACCEPTED'" +
                "where user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql,
                userId,
                friendId
        );
        jdbcTemplate.update(sql,
                friendId,
                userId
        );
    }

    @Override
    public void declineFriendRequest(int userId, int friendId) {
        String sql = "UPDATE user_friend SET " +
                "status = 'NOT ACCEPTED'" +
                "where user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql,
                userId,
                friendId
        );
        jdbcTemplate.update(sql,
                friendId,
                userId
        );
    }

     */
}
