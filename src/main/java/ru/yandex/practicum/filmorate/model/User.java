package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.validation.IsNotHaveWhiteSpaces;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    private Integer id;
    @NotBlank(message = "email не может быть пустым!")
    @Email(message = "Неверный формат email!")
    private String email;
    @NotBlank(message = "email не может быть пустым!")
    @IsNotHaveWhiteSpaces(message = "login содержит пробелы!")
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
}
