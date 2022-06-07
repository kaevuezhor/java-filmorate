package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.DbFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.DbUserStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmoRateApplicationTests {
        private final DbUserStorage userStorage;
        private final DbFilmStorage filmStorage;

        @Test
        public void test() {
                User user = new User(666,
                        "e@mail.ru",
                        "login",
                        "name",
                        LocalDate.of(2007, 9, 3),
                        new HashSet<>());
                User anotherUser = new User(228,
                        "ya@ya.ru",
                        "nigol",
                        "eman",
                        LocalDate.of(2007, 9, 3),
                        new HashSet<>());
                Film film = new Film(
                        420,
                        "film",
                        "wow",
                        LocalDate.of(2007, 9, 3),
                        111,
                        new HashSet<>(),
                        10,
                        new MpaRating("R")
                );
                Film anotherFilm = new Film(
                        359,
                        "mlif",
                        "owo",
                        LocalDate.of(2007, 9, 3),
                        999,
                        new HashSet<>(),
                        1,
                        new MpaRating("G")
                );
                Optional<User> userOptional = Optional.of(userStorage.create(user));
                assertThat(userOptional)
                        .isPresent()
                        .hasValueSatisfying(u ->
                                assertThat(u).hasFieldOrPropertyWithValue("id", 1)
                        );

                userOptional = Optional.of(userStorage.create(anotherUser));
                assertThat(userOptional)
                        .isPresent()
                        .hasValueSatisfying(u ->
                                assertThat(u).hasFieldOrPropertyWithValue("id", 2)
                        );

                Optional<Film> filmOptional = Optional.of(filmStorage.create(film));
                assertThat(filmOptional)
                        .isPresent()
                        .hasValueSatisfying(f ->
                                assertThat(f).hasFieldOrPropertyWithValue("id", 1)
                        );

                filmOptional = Optional.of(filmStorage.create(anotherFilm));
                assertThat(filmOptional)
                        .isPresent()
                        .hasValueSatisfying(f ->
                                assertThat(f).hasFieldOrPropertyWithValue("id", 2)
                        );

                userOptional = userStorage.find(1);
                assertThat(userOptional)
                        .isPresent()
                        .hasValueSatisfying(u ->
                                assertThat(u).hasFieldOrPropertyWithValue("id", 1)
                        );

                filmOptional = filmStorage.find(1);
                assertThat(filmOptional)
                        .isPresent()
                        .hasValueSatisfying(u ->
                                assertThat(u).hasFieldOrPropertyWithValue("id", 1)
                        );

                User updatedUser = user = new User(1,
                        "e@mail.ru",
                        "nagibator",
                        "vasyanpro",
                        LocalDate.of(2007,9,3),
                        new HashSet<>());
                userOptional = Optional.of(userStorage.update(updatedUser));
                assertThat(userOptional)
                        .isPresent()
                        .hasValueSatisfying(u ->
                                assertThat(u).hasFieldOrPropertyWithValue("id", 1)
                        )
                        .hasValueSatisfying(u ->
                                assertThat(u).hasFieldOrPropertyWithValue("login", "nagibator")
                        )
                        .hasValueSatisfying(u ->
                                assertThat(u).hasFieldOrPropertyWithValue("name", "vasyanpro")
                        );

                User downgradedUser = new User(1,
                        "e@mail.ru",
                        "login",
                        "name",
                        LocalDate.of(2007,9,3),
                        new HashSet<>());
                userOptional = Optional.of(userStorage.update(downgradedUser));
                assertThat(userOptional)
                        .isPresent()
                        .hasValueSatisfying(u ->
                                assertThat(u).hasFieldOrPropertyWithValue("id", 1)
                        )
                        .hasValueSatisfying(u ->
                                assertThat(u).hasFieldOrPropertyWithValue("login", "login")
                        )
                        .hasValueSatisfying(u ->
                                assertThat(u).hasFieldOrPropertyWithValue("name", "name")
                        );

                Film updatedFilm = new Film(
                        1,
                        "film",
                        "wowowowowowow",
                        LocalDate.of(2007,9,3),
                        111,
                        new HashSet<>(),
                        10,
                        new MpaRating("R")
                );

                filmOptional = Optional.of(filmStorage.update(updatedFilm));
                assertThat(filmOptional)
                        .isPresent()
                        .hasValueSatisfying(u ->
                                assertThat(u).hasFieldOrPropertyWithValue("id", 1)
                        )
                        .hasValueSatisfying(u ->
                                assertThat(u).hasFieldOrPropertyWithValue("description", "wowowowowowow")
                        );

                Film downgradedFilm = new Film(
                        1,
                        "film",
                        "wow",
                        LocalDate.of(2007,9,3),
                        111,
                        new HashSet<>(),
                        10,
                        new MpaRating("R")
                );
                filmOptional = Optional.of(filmStorage.update(downgradedFilm));
                assertThat(filmOptional)
                        .isPresent()
                        .hasValueSatisfying(u ->
                                assertThat(u).hasFieldOrPropertyWithValue("id", 1)
                        )
                        .hasValueSatisfying(u ->
                                assertThat(u).hasFieldOrPropertyWithValue("description", "wow")
                        );

                filmStorage.delete(2);
                filmOptional = filmStorage.find(2);
                assertThat(filmOptional)
                        .isEmpty();

                userStorage.delete(2);
                userOptional = userStorage.find(2);
                assertThat(userOptional)
                        .isEmpty();
        }




}