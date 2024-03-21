package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
            log.warn("Ошибка добавления пользователя!");
            throw new AlreadyExistsException("Такой пользователь уже добавлен!");
        }
        log.info("Добавлен пользователь с id={}!", user.getId());
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Старт операции обновления пользователя: {}", user.toString());
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            log.warn("Ошибка обновления пользователя!");
            throw new NotFoundException("Такой пользователь не найден!");
        }
        log.info("Обновлен пользователь с id={}!", user.getId());
        return user;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

}
