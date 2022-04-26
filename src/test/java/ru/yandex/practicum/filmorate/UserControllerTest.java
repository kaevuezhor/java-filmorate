package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    UserController controller;

    @BeforeEach
    public void beforeEach() {
        controller = new UserController();
    }

    @Test
    public void shouldGetAllUsers() {
        User user = new User(
                1,
                "email@ya.ru",
                "login",
                "nickname",
                LocalDate.of(2000,1,1));
        User anotherUser = new User(
                2,
                "another_email@ya.ru",
                "another_login",
                "another_nickname",
                LocalDate.of(2000,1,2));
        controller.create(user);
        controller.create(anotherUser);
        assertTrue(List.of(user, anotherUser).containsAll(controller.findAll().values()));
    }

    @Test
    public void shouldNotCreateUserWithBlankEmail() {
        User user = new User(
                1,
                "",
                "login",
                "nickname",
                LocalDate.of(2000,1,1));
        assertThrows(ValidateException.class, () -> {
            controller.create(user);
        });
    }

    @Test
    public void shouldNotCreateUserWithWrongEmail() {
        User user = new User(
                1,
                "email",
                "login",
                "nickname",
                LocalDate.of(2000,1,1));
        assertThrows(ValidateException.class, () -> {
            controller.create(user);
        });
    }

    @Test
    public void shouldNotCreateUserWithBlankLogin() {
        User user = new User(
                1,
                "email@ya.ru",
                "",
                "nickname",
                LocalDate.of(2000,1,1));
        assertThrows(ValidateException.class, () -> {
            controller.create(user);
        });
    }

    @Test
    public void shouldNotCreateUserWithSpaceInLogin() {
        User user = new User(
                1,
                "email@ya.ru",
                "log in",
                "nickname",
                LocalDate.of(2000,1,1));
        assertThrows(ValidateException.class, () -> {
            controller.create(user);
        });
    }

    @Test
    public void shouldNotCreateUserWithWrongBirthDate() {
        User user = new User(
                1,
                "email@ya.ru",
                "login",
                "nickname",
                LocalDate.of(2025,10,25));
        assertThrows(ValidateException.class, () -> {
            controller.create(user);
        });
    }

}
