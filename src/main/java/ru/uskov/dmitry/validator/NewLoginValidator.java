package ru.uskov.dmitry.validator;

import org.springframework.beans.factory.annotation.Autowired;
import ru.uskov.dmitry.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Dmitry on 24.04.2017.
 */
public class NewLoginValidator implements ConstraintValidator<NewLogin, String> {

    @Autowired
    UserService userService;

    @Override
    public void initialize(NewLogin newLogin) {

    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("Валидирование логина");
        System.out.println(userService);
        return false;
    }
}