package ru.uskov.dmitry.dao;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.sql.dml.SQLUpdateClause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.uskov.dmitry.annotation.TransactionalMandatory;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.common.CollectionUtils;
import ru.uskov.dmitry.domain.QGeneralSetting;
import ru.uskov.dmitry.domain.QSpecialSetting;
import ru.uskov.dmitry.entity.GeneralSettings;
import ru.uskov.dmitry.entity.SpecialSettings;
import ru.uskov.dmitry.enums.SpecialSettingsType;

import java.util.List;

/**
 * Created by Dmitry on 02.06.2017.
 */
@Repository
public class SettingsDao extends AbstractDao {
    private static final Logger logger = LoggerFactory.getLogger(SettingsDao.class);

    private static final QGeneralSetting qGeneralSetting = QGeneralSetting.generalSetting;
    private static final QSpecialSetting qSpecialSetting = QSpecialSetting.specialSetting;

    @TransactionalSupport
    public List<GeneralSettings> loadAll() {
        return queryFactory.select(getGeneralSettingsProjection()).from(qGeneralSetting).fetch();
    }

    @TransactionalMandatory
    public long update(List<GeneralSettings> settings) {
        if (CollectionUtils.isEmpty(settings)) {
            return 0;
        }
        SQLUpdateClause update = queryFactory.update(qGeneralSetting);
        settings.stream().forEach(s -> update.set(qGeneralSetting.settingValue, s.getValue()).where(qGeneralSetting.settingName.eq(s.getType().name())).addBatch());
        return update.execute();
    }

    public void update(SpecialSettings specialSettingsEntity) {
        queryFactory.update(qSpecialSetting).set(qSpecialSetting.settingValue, specialSettingsEntity.getValue()).where(qSpecialSetting.settingName.eq(specialSettingsEntity.getType().name())).execute();
    }

    public SpecialSettings loadSpecialSetting(SpecialSettingsType type) {
        List<SpecialSettings> settingss = queryFactory.select(getSpecialSettingProjection()).from(qSpecialSetting).where(qSpecialSetting.settingName.eq(type.name())).fetch();
        if (CollectionUtils.isEmpty(settingss)) {
            return null;
        } else {
            return settingss.get(0);
        }
    }

    private ConstructorExpression<SpecialSettings> getSpecialSettingProjection() {
        return Projections.constructor(SpecialSettings.class
                , qSpecialSetting.settingName
                , qSpecialSetting.settingValue
        );
    }

    private ConstructorExpression<GeneralSettings> getGeneralSettingsProjection() {
        return Projections.constructor(GeneralSettings.class
                , qGeneralSetting.settingName
                , qGeneralSetting.settingValue
        );
    }
}