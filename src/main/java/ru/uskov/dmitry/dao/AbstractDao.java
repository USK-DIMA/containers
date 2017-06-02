package ru.uskov.dmitry.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import ru.uskov.dmitry.annotation.TransactionalSupport;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Dmitry on 11.03.2017.
 */
abstract public class AbstractDao {
    @Autowired
    protected SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected void delete(Class clazz, Long entityId) {
        Session session = getCurrentSession();
        Object entity = session.load(clazz, entityId);
        session.delete(entity);
    }

    @TransactionalSupport
    protected <T> List<T> getAll(Class<T> clazz) {
        return getCurrentSession().createCriteria(clazz).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    protected <T> List<T> listByIds(Class<T> entityType, Collection<? extends Serializable> ids) {
        Session session = getCurrentSession();
        String idPropertyName = session.getSessionFactory().getClassMetadata(entityType).getIdentifierPropertyName();

        Criteria criteria = session.createCriteria(entityType)
                .add(Restrictions.in(idPropertyName, ids));

        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    protected Criteria fullCriteria(Class clazz, Map<String, Object> map) {
        Criteria criteria = getCurrentSession().createCriteria(clazz);
        map.keySet().forEach(k -> criteria.add(Restrictions.eq(k, map.get(k))));
        return criteria;
    }


}
