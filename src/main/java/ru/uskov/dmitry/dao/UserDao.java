package ru.uskov.dmitry.dao;

import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.uskov.dmitry.annotation.TransactionaMandatory;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Dmitry on 11.03.2017.
 */
@Repository
public class UserDao extends AbstractDao {

    @TransactionalSupport
    public List<User> getAll() {
        return getCurrentSession().createCriteria(User.class).list();
    }

    @TransactionaMandatory
    public void insertUsers(List<User> users) {
        Session session = getCurrentSession();
        users.stream().forEach(u -> session.persist(u));
    }

    public List<User> getByFieldEq(Map<String, Object> map) {
        Criteria criteria = getCurrentSession().createCriteria(User.class);
        map.keySet().stream().forEach(k -> criteria.add(Restrictions.eq(k, map.get(k))));
        return criteria.list();
    }
}
