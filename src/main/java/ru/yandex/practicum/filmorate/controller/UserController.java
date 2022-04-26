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
        if (user.getNickname().isBlank()) {
            user.setNickname(user.getLogin());
        }
        if (user.getBirthdate().isAfter(LocalDate.now())) {
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
                user.getNickname(),
                user.getBirthdate()
        );
    }

}
