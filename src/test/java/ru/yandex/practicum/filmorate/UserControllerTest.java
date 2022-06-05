package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    UserController controller;
    UserStorage storage;
    UserService service;

    @BeforeEach
    public void beforeEach() {
        storage = new InMemoryUserStorage();
        service = new UserService(storage);
        controller = new UserController(service);
    }
    /*
    @Test
    public void shouldGetAllUsers() throws ValidationException {
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
        assertTrue(List.of(user, anotherUser).containsAll(controller.findAll()));
    }

    @Test
    public void shouldNotCreateUserWithBlankEmail() {
        User user = new User(
                1,
                "",
                "login",
                "nickname",
                LocalDate.of(2000,1,1));
        assertThrows(ValidationException.class, () -> {
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
        assertThrows(ValidationException.class, () -> {
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
        assertThrows(ValidationException.class, () -> {
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
        assertThrows(ValidationException.class, () -> {
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
        assertThrows(ValidationException.class, () -> {
            controller.create(user);
        });
    }

     */

}
