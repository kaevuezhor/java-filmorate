package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@RequestMapping("/users")
@RestController
@Slf4j
public class UserController extends Controller<User>{

    @Override
    protected Boolean validate(User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            return false;
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            return false;
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            return false;
        }
        return true;
    }

    @Override
    protected User convert(int id, User user) {
        return new User(
                id,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );
    }

}
