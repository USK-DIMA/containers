package ru.uskov.dmitry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uskov.dmitry.annotation.TransactionalService;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.common.CollectionUtils;
import ru.uskov.dmitry.dao.UserDao;
import ru.uskov.dmitry.entity.User;
import ru.uskov.dmitry.enums.UserRole;
import ru.uskov.dmitry.exception.EmailAlreadyExistException;
import ru.uskov.dmitry.exception.LoginAlreadyExistException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Dmitry on 11.03.2017.
 */
@Service
public class UserService extends AbstractService {

    @Autowired
    UserDao userDao;

    @TransactionalService
    public List<User> loadAllUsers() {
        return userDao.getAll();
    }

    @TransactionalService
    public void saveTestUser() {
        final int size = 10;
        List<User> users = new ArrayList<>(size);

        {
            User user = new User();
            user.setActive(true);
            user.setEmail("admin@gmail.com");
            user.setLogin("admin");
            user.setName("Усков Дмитрий Юрьевич");
            user.setPassword("123");
            Set<UserRole> roleSet = new HashSet<>();
            roleSet.add(UserRole.ROLE_ADMIN);
            roleSet.add(UserRole.ROLE_MANAGER);
            user.setRoles(roleSet);
            users.add(user);
        }

        for (int i = 0; i < size; i++) {
            User user = new User();
            user.setActive(i % 2 == 0);
            user.setEmail("user" + i + "@gmail.com");
            user.setLogin("login" + i);
            user.setName("name" + i);
            user.setPassword("pass");
            Set<UserRole> roleSet = new HashSet<>();
            roleSet.add(UserRole.ROLE_ADMIN);
            roleSet.add(UserRole.ROLE_MANAGER);
            user.setRoles(roleSet);
            users.add(user);
        }
        userDao.insertUsers(users);
    }

    @TransactionalService
    public User loadActiveUserByLogin(String login) {
        List<User> users = userDao.getByFieldEq(getMap("login", login).end("active", new Boolean(true)));
        return CollectionUtils.getFirst(users);
    }

    @TransactionalService
    public void createUser(User newUser) throws EmailAlreadyExistException, LoginAlreadyExistException {
        checkLoginAndEmail(newUser);
        userDao.insertUsers(Collections.singletonList(newUser));
    }

    @TransactionalSupport
    private void checkLoginAndEmail(User newUser) throws EmailAlreadyExistException, LoginAlreadyExistException {
        List<User> allUsers = loadAllUsers().stream().filter(u -> !u.getId().equals(newUser.getId())).collect(Collectors.toList());
        List<String> emails = allUsers.stream().map(u -> u.getEmail()).collect(Collectors.toList());
        List<String> logins = allUsers.stream().map(u -> u.getLogin()).collect(Collectors.toList());
        if (emails.contains(newUser.getEmail())) {
            throw new EmailAlreadyExistException("Данный Email уже присутсвует в системе");
        }

        if (logins.contains(newUser.getLogin())) {
            throw new LoginAlreadyExistException("Выбранный логин уже занят кем-то другим");
        }
    }

    @TransactionalSupport
    public boolean loginExist(String login) {
        return userDao.getByFieldEqCount(getMapEnd("login", login)) != 0;
    }

    @TransactionalSupport
    public boolean emailExist(String email) {
        return userDao.getByFieldEqCount(getMapEnd("email", email)) != 0;
    }

    @TransactionalService
    public void delete(Long userId) {
        userDao.delete(userId);
    }

    @TransactionalService
    public void setActive(Long userId, Boolean active) {
        userDao.setActive(userId, active);
    }


    @TransactionalService
    public void updateUser(User user) throws LoginAlreadyExistException, EmailAlreadyExistException {
        checkLoginAndEmail(user);
        User forUpdate = userDao.getUser(user.getId());
        forUpdate.setComment(user.getComment());
        forUpdate.setEmail(user.getEmail());
        forUpdate.setName(user.getName());
        forUpdate.setLogin(user.getLogin());
        forUpdate.setRoles(user.getRoles());
        userDao.update(forUpdate);
    }

    @TransactionalSupport
    public User getUser(Long userId) {
        return userDao.getUser(userId);
    }
}
