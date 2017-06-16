package ru.uskov.dmitry.validator.constraints;

import org.springframework.beans.factory.annotation.Autowired;
import ru.uskov.dmitry.dao.UserDao;
import ru.uskov.dmitry.entity.User;
import ru.uskov.dmitry.validator.annotataions.NewUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dmitry on 15.06.2017.
 */
public class NewUserConstraint implements ConstraintValidator<NewUser, User> {


    @Autowired
    UserDao userDao;

    @Override
    public void initialize(NewUser newUser) {
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        Integer userId = user.getId();
        String email = user.getEmail();
        String login = user.getLogin();
        List<User> allUsers = userDao.loadAllWithout(userId);
        List<String> emails = allUsers.stream().map(u -> u.getEmail()).collect(Collectors.toList());
        List<String> logins = allUsers.stream().map(u -> u.getLogin()).collect(Collectors.toList());

        boolean valid = true;
        if (emails.contains(email)) {
            valid = false;
            constraintValidatorContext.buildConstraintViolationWithTemplate("Данный Email уже присутсвует в системе").addBeanNode();
        }

        if (logins.contains(login)) {
            valid = false;
            constraintValidatorContext.buildConstraintViolationWithTemplate("Выбранный логин уже занят кем-то другим").addBeanNode();
        }

        return valid;
    }
}