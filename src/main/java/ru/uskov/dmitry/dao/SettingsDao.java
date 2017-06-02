package ru.uskov.dmitry.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.uskov.dmitry.annotation.TransactionalMandatory;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.common.CollectionUtils;
import ru.uskov.dmitry.entity.GeneralSettings;
import ru.uskov.dmitry.entity.SpecialSettings;
import ru.uskov.dmitry.enums.SpecialSettingsType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dmitry on 02.06.2017.
 */
@Repository
public class SettingsDao extends AbstractDao {
    private static final Logger logger = LoggerFactory.getLogger(SettingsDao.class);


    @TransactionalSupport
    public List<GeneralSettings> loadAll() {
        return this.getAll(GeneralSettings.class);
    }

    @TransactionalMandatory
    public void save(List<GeneralSettings> settings) {
        settings.stream().forEach(s -> sessionFactory.getCurrentSession().saveOrUpdate(s));
    }

    public void save(SpecialSettings specialSettingsEntity) {
        sessionFactory.getCurrentSession().saveOrUpdate(specialSettingsEntity);
    }

    public SpecialSettings loadSpecialSetting(SpecialSettingsType type) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        List<SpecialSettings> entities = fullCriteria(SpecialSettings.class, map).list();
        if (!CollectionUtils.isEmpty(entities)) {
            return entities.get(0);
        } else {
            return null;
        }
    }
}