package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Primary
public class DbUserStorage implements UserStorage{

    private final JdbcTemplate jdbcTemplate;

    public DbUserStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public HashMap<Integer, User> findAll() {
        String sql = "SELECT * FROM users ;";
        return new HashMap<>(jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs)).stream()
                .map(this::loadFriends)
                .collect(Collectors.toMap((u) -> u.getId(), (u) -> u)));
    }

    @Override
    public User create(User user) {
        String sql = "INSERT INTO users (email, login, name, birthday)" +
                "VALUES (?, ?, ?, ?);" ;
        SqlRowSet sqlRows = jdbcTemplate.queryForRowSet("SELECT COUNT(user_id) AS count FROM users;");
        sqlRows.next();
        user.setId(sqlRows.getInt("count") + 1);
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );
        return find(user.getId()).get();
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE users SET " +
                "email = ?, login = ?, name = ?, birthday = ?" +
                "where user_id = ?";
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );
        return find(user.getId()).get();
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE user_id = ? ;";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<User> find(int id) {
        String sql = "SELECT * FROM users WHERE user_id = ? ;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), id).stream()
                        .map(this::loadFriends)
                        .findFirst();
    }

    private User makeUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("user_id"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getDate("birthday").toLocalDate(),
                new HashSet<>()
        );
    }

    private User loadFriends(User user) {
        SqlRowSet sqlRows = jdbcTemplate.queryForRowSet(
                "SELECT friend_id FROM user_friend WHERE user_id = ?",
                user.getId()
        );
        while (sqlRows.next()) {
            user.getFriends().add(sqlRows.getInt("friend_id"));
        }
        return user;
    }
}
