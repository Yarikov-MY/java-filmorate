package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.validation.IsAfter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
@AllArgsConstructor
public class Film {
    private Integer id;
    @NotBlank(message = "Название не может быть пустым!")
    private String name;
    @Size(max = 200, message = "Максимальная длина описания — 200 символов!")
    private String description;
    @IsAfter(current = "1895-12-27")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной!")
    private Integer duration;
}
