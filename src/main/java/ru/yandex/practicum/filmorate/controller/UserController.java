package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequestMapping("/users")
@RestController
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> find(@PathVariable("id") Integer id) throws UserNotFoundException {
        return userService.find(id);
    }

    @PostMapping()
    public User create(@Valid @RequestBody User user) throws ValidationException {
        if (!validate(user)) {
            throw new ValidationException("Ошибка валидации");
        }
        return userService.create(user);
    }

    @PutMapping()
    public User update(@Valid @RequestBody User user) throws ValidationException, UserNotFoundException {
        if (!validate(user)) {
            throw new ValidationException("Ошибка валидации");
        }
        return userService.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") Integer userId,
                          @PathVariable("friendId") Integer friendId
    ) throws UserNotFoundException {
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") Integer userId,
                             @PathVariable("friendId") Integer friendId
    ) throws UserNotFoundException {
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable("id") Integer userId) throws UserNotFoundException {
        return userService.getFriends(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") Integer userId,
                             @PathVariable("otherId") Integer otherId
    ) throws UserNotFoundException {
        return userService.getCommonFriends(userId, otherId);
    }

    protected Boolean validate(User user) {
        if (!StringUtils.hasText(user.getEmail()) || !user.getEmail().contains("@")) {
            return false;
        }
        if (!StringUtils.hasText(user.getLogin()) || user.getLogin().contains(" ")) {
            return false;
        }
        if (!StringUtils.hasText(user.getName())) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            return false;
        }
        return true;
    }
}
