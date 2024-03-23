package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        log.info("Старт операции добавления пользователя: {}", user.toString());
        if (user.getId() == null || !users.containsKey(user.getId())) {
            user.setId(id.incrementAndGet());
            users.put(user.getId(), user);
        } else {
            String message = String.format("Пользователь с id={%s} уже добавлен!", user.getId());
            log.warn(message);
            throw new AlreadyExistsException(message);
        }
        log.info("Добавлен пользователь с id={}!", user.getId());
        return replaceNameToLoginIfNameNull(user);
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Старт операции обновления пользователя: {}", user.toString());
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            String message = String.format("Пользователь с id={%s} не найден!", user.getId());
            log.warn(message);
            throw new NotFoundException(message);
        }
        log.info("Обновлен пользователь с id={}!", user.getId());
        return replaceNameToLoginIfNameNull(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values()).stream().map(this::replaceNameToLoginIfNameNull).collect(Collectors.toList());
    }

    private User replaceNameToLoginIfNameNull(User user) {
        if (user.getName() == null) {
            return new User(user.getId(), user.getEmail(), user.getLogin(), user.getLogin(), user.getBirthday());
        } else {
            return user;
        }
    }

}
