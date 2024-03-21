package ru.yandex.practicum.filmorate.exceptions;


public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(final String message) {
        super(message);
    }
}
