package ru.yandex.practicum.filmorate.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WhiteSpacesValidator implements ConstraintValidator<IsNotHaveWhiteSpaces, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !s.contains(" ");
    }
}
