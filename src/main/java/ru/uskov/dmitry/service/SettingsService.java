package ru.uskov.dmitry.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uskov.dmitry.annotation.TransactionalService;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.controller.form.GeneralSettings;
import ru.uskov.dmitry.dao.SettingsDao;
import ru.uskov.dmitry.entity.SpecialSettings;
import ru.uskov.dmitry.enums.GeneralSettingType;
import ru.uskov.dmitry.enums.SpecialSettingsType;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Dmitry on 02.06.2017.
 */
@Service
public class SettingsService {
    private static final Logger logger = LoggerFactory.getLogger(SettingsService.class);


    @Autowired
    private SettingsDao settingsDao;

    @TransactionalService
    public void saveSettings(GeneralSettings settings) {
        settingsDao.update(formToEntity(settings));
    }

    @TransactionalService
    public void saveStartPointCoordinate(Double[] coordinate) {
        settingsDao.update(new SpecialSettings(SpecialSettingsType.START_POINT_COORDINATE, coordinate));
    }

    @TransactionalService
    public GeneralSettings loadGenerateSettings() {
        List<ru.uskov.dmitry.entity.GeneralSettings> settingsEntity = settingsDao.loadAll();
        return entityToForm(settingsEntity);
    }

    @TransactionalSupport
    public Double[] getStartPoint() {
        SpecialSettings entity = settingsDao.loadSpecialSetting(SpecialSettingsType.START_POINT_COORDINATE);
        if (entity == null) {
            return (Double[]) SpecialSettingsType.START_POINT_COORDINATE.getDefault();
        } else {
            return (Double[]) SpecialSettingsType.START_POINT_COORDINATE.parse(entity.getValue());
        }
    }

    private List<ru.uskov.dmitry.entity.GeneralSettings> formToEntity(GeneralSettings settings) {
        List<ru.uskov.dmitry.entity.GeneralSettings> settingsEntities = new LinkedList<>();
        settingsEntities.add(new ru.uskov.dmitry.entity.GeneralSettings(GeneralSettingType.UPDATE_TIME, settings.getUpdateTimeout()));
        return settingsEntities;
    }


    private GeneralSettings entityToForm(List<ru.uskov.dmitry.entity.GeneralSettings> settingsEntity) {
        GeneralSettings settings = new GeneralSettings();
        settings.setUpdateTimeout((Long) getSettingValue(settingsEntity, GeneralSettingType.UPDATE_TIME));
        return settings;
    }

    private Object getSettingValue(GeneralSettingType type) {
        List<ru.uskov.dmitry.entity.GeneralSettings> settingsEntity = settingsDao.loadAll();
        return getSettingValue(settingsEntity, type);
    }

    private Object getSettingValue(List<ru.uskov.dmitry.entity.GeneralSettings> settingsEntity, GeneralSettingType type) {
        Optional<ru.uskov.dmitry.entity.GeneralSettings> entity = settingsEntity.stream().filter(s -> s.getType().equals(type)).findFirst();
        if (entity.isPresent()) {
            return type.parse(entity.get().getValue());
        } else {
            return type.getDefault();
        }
    }

}