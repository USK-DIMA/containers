package ru.uskov.dmitry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uskov.dmitry.annotation.TransactionalService;
import ru.uskov.dmitry.common.CollectionUtils;
import ru.uskov.dmitry.dao.UserDao;
import ru.uskov.dmitry.entity.Contragent;
import ru.uskov.dmitry.entity.User;
import ru.uskov.dmitry.enums.ServiceRole;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public void saveTestUser(Contragent contragent) {
        final int size = 10;
        List<User> users = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            User user = new User();
            user.setActive(i % 2 == 0);
            user.setContragent(contragent);
            user.setEmail("user" + i + "@gmail.com");
            user.setLogin("login" + i);
            user.setName("name" + i);
            user.setPassword("pass");
            Set<ServiceRole> roleSet = new HashSet<>();
            roleSet.add(ServiceRole.ROLE_ADMIN);
            roleSet.add(ServiceRole.ROLE_USER);
            user.setServiceRoleSet(roleSet);
            users.add(user);
        }
        userDao.insertUsers(users);
    }

    @TransactionalService
    public User loadActiveUserByLogin(String login) {
        List<User> users = userDao.getByFieldEq(getMap("login", login).end("active", new Boolean(true)));
        return CollectionUtils.getFirst(users);
    }
}
