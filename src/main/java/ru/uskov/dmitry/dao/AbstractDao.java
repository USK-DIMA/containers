package ru.uskov.dmitry.dao;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import ru.uskov.dmitry.entity.User;

/**
 * Created by Dmitry on 11.03.2017.
 */
abstract public class AbstractDao {
    @Autowired
    protected SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected void delete(Class<User> clazz, Long entityId) {
        Session session = getCurrentSession();
        Object entity = session.load(clazz, entityId);
        session.delete(entity);
    }

}
