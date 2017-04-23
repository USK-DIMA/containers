package ru.uskov.dmitry.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.entity.User;

import java.util.List;

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

    @TransactionalSupport
    protected <T> List<T> getAll(Class<T> clazz) {
        return getCurrentSession().createCriteria(clazz).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }


}
