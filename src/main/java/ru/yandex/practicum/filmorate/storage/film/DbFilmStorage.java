package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Primary
public class DbFilmStorage implements FilmStorage{

    private final JdbcTemplate jdbcTemplate;

    public DbFilmStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public HashMap<Integer, Film> findAll() {
        String sql = "SELECT * FROM film";
        return new HashMap<>(jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs)).stream()
                .map(this::loadLikes)
                .collect(Collectors.toMap((f) -> f.getId(), (f) -> f)));
    }

    @Override
    public Film create(Film film) {
        String sql = "INSERT INTO film (name, description, release_date, duration, rate, rating)" +
                "VALUES (?, ?, ?, ?, ?, ?);" ;
        SqlRowSet sqlRows = jdbcTemplate.queryForRowSet("SELECT COUNT(film_id) AS count FROM film ;");
        sqlRows.next();
        film.setId(sqlRows.getInt("count") + 1);
        jdbcTemplate.update(sql,
                            film.getName(),
                            film.getDescription(),
                            film.getReleaseDate(),
                            film.getDuration(),
                            film.getRate(),
                            film.getMpa().getName()
        );
        return find(film.getId()).get();
    }

    @Override
    public Film update(Film film) {
        String sql = "UPDATE film SET " +
                "name = ?, description = ?, release_date = ?, duration = ?, rate = ?, rating = ? " +
                "where film_id = ?";
        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getMpa().getName(),
                film.getId()
        );
        return find(film.getId()).get();
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM film WHERE film_id = ? ;";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Film> find(int id) {
        String sql = "SELECT * FROM film WHERE film_id = ? ;";
        return Optional.of(
                jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs), id).stream()
                        .map(this::loadLikes)
                        .collect(Collectors.toList())
                        .get(0)
        );
    }

    @Override
    public void addLike(int filmId, int userId) {
        String sql = "INSERT INTO film_likes (film_id, user_id)" +
                "VALUES (?, ?);" ;
        jdbcTemplate.update(sql,
                filmId,
                userId
        );
    }

    @Override
    public void removeLike(int filmId, int userId) {
        String sql = "DELETE FROM film_likes WHERE film_id = ? AND user_id = ?;" ;
        jdbcTemplate.update(sql,
                filmId,
                userId
        );
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        return new Film(
                rs.getInt("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                new HashSet<>(),
                rs.getInt("rate"),
                new MpaRating(rs.getString("rating"))
        );
    }

    private Film loadLikes(Film film) {
        SqlRowSet sqlRows = jdbcTemplate.queryForRowSet(
                "SELECT user_id FROM film_likes WHERE film_id = ?;",
                film.getId()
        );
        while (sqlRows.next()) {
            film.getUsersLiked().add(sqlRows.getInt("user_id"));
        }
        return film;
    }
}
