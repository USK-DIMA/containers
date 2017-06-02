package ru.uskov.dmitry.entity;

import ru.uskov.dmitry.enums.GeneralSettingType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * Created by Dmitry on 02.06.2017.
 */
@Entity
public class GeneralSettings extends AbstractEntity {

    @Id
    @Enumerated(EnumType.STRING)
    private GeneralSettingType type;
    private String value;

    public GeneralSettings(GeneralSettingType type, Object value) {
        this.type = type;
        this.value = type.serialize(value);
    }

    public GeneralSettings() {
    }


    public GeneralSettingType getType() {
        return type;
    }

    public void setType(GeneralSettingType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}