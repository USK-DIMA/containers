package ru.uskov.dmitry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uskov.dmitry.annotation.TransactionalMandatory;
import ru.uskov.dmitry.annotation.TransactionalService;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.common.Common;
import ru.uskov.dmitry.controller.form.ChangePasswordForm;
import ru.uskov.dmitry.dao.DeviceDao;
import ru.uskov.dmitry.dao.UserDao;
import ru.uskov.dmitry.entity.User;
import ru.uskov.dmitry.enums.UserRole;
import ru.uskov.dmitry.exception.ConfirmPasswordException;
import ru.uskov.dmitry.exception.IncorrectNewUserException;
import ru.uskov.dmitry.exception.IncorrectPasswordException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by Dmitry on 11.03.2017.
 */
@Service
public class UserService extends AbstractService {

    @Autowired
    Validator validator;

    @Autowired
    UserDao userDao;

    @Autowired
    DeviceDao deviceDao;

    @Autowired
    Common common;

    @TransactionalService
    public List<User> loadAllUsers() {
        return userDao.getAllWithRolesAndDeviceCount();
    }

    @TransactionalService
    public User loadUserByLoginWithRoles(String login, boolean onlyActive) {
        User user = userDao.getByLoginWithRoles(login, onlyActive);
        return user;
    }

    @TransactionalService
    public void createUser(User newUser) throws IncorrectNewUserException {
        validateOrThrow(newUser);
        Integer userId = userDao.insertUser(newUser);
        userDao.insertUserRoles(userId, newUser.getRoles());
    }

    private void validateOrThrow(User newUser) throws IncorrectNewUserException {
        Set<ConstraintViolation<User>> invalids = validator.validate(newUser);
        if (invalids.size() > 0) {
            throw new IncorrectNewUserException(invalids.iterator().next().getMessage());
        }
    }

    @TransactionalSupport
    public boolean loginExist(String login) {
        return userDao.loginExist(login);
    }

    @TransactionalSupport
    public boolean emailExist(String email) {
        return userDao.emailExist(email);
    }

    @TransactionalService
    public void delete(Integer userId) {
        userDao.delete(userId);
    }

    @TransactionalService
    public void setActive(Integer userId, Boolean active) {
        userDao.setActive(userId, active);
    }


    @TransactionalService
    public void updateUser(User user, Set<Integer> deviceId) throws IncorrectNewUserException {
        validateOrThrow(user);
        userDao.update(user.getId(), user.getLogin(), user.getEmail(), user.getName(), user.getComment());

        saveUserRoles(user.getId(), user.getRoles());
        saveUserDevices(user.getId(), deviceId);
    }

    @TransactionalMandatory
    private void saveUserDevices(Integer userId, Collection<Integer> deviceIds) {
        userDao.deleteUserDeviceMap(userId);
        userDao.insertUserDeviceMap(userId, deviceIds);

    }

    @TransactionalMandatory
    private void saveUserRoles(Integer userId, Collection<UserRole> roles) {
        userDao.deleteRoles(userId);
        userDao.insertUserRoles(userId, roles);
    }

    @TransactionalSupport
    public User getUser(Integer userId) {
        User user = userDao.getUsersWithRoles(Collections.singleton(userId)).get(0);
        return user;
    }

    @TransactionalService
    public void changePassword(Integer userId, ChangePasswordForm changePasswordForm) throws IncorrectPasswordException, ConfirmPasswordException {
        User user = userDao.getUser(userId);
        if (!changePasswordForm.getOldPassword().equals(user.getPassword())) {
            throw new IncorrectPasswordException();
        }
        if (!changePasswordForm.getNewPassword().equals(changePasswordForm.getConfirmPassword())) {
            throw new ConfirmPasswordException();
        }

        userDao.updatePassword(userId, changePasswordForm.getNewPassword());
        common.updateCurrentUser();
    }

    @TransactionalSupport
    public boolean validUserPassword(Integer userId, String password) {
        return userDao.getUser(userId).getPassword().equals(password);
    }

    @TransactionalService
    public void updateCurrentUser(String login, String email, String name) throws IncorrectNewUserException {
        User user = common.getUpdatedCurrentUser();
        validateOrThrow(new User(user.getId(), login, email));
        userDao.update(user.getId(), login, email, name, null);
        common.updateCurrentUser();
    }


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserWithDevicesIds(Integer userId) {
        return userDao.getUserWithDevicesIds(userId);
    }
}
