package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);

    @PostMapping("/films")
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Старт операции добавления фильма: {}", film.toString());
        if (film.getId() == null || !films.containsKey(film.getId())) {
            film.setId(id.incrementAndGet());
            films.put(film.getId(), film);
        } else {
            log.warn("Ошибка добавления фильма!");
            throw new AlreadyExistsException("Такой фильм уже добавлен!");
        }
        log.info("Добавлен фильм с id={}!", film.getId());
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Старт операции обновления фильма: {}", film.toString());
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            log.warn("Ошибка обновления фильма с id={}!", film.getId());
            throw new NotFoundException("Такой фильм не найден!");
        }
        log.info("Обновлен фильм с id={}!", film.getId());
        return film;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }
}
