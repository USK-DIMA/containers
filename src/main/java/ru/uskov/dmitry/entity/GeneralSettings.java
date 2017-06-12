package ru.uskov.dmitry.entity;

import com.querydsl.core.annotations.QueryProjection;
import ru.uskov.dmitry.enums.GeneralSettingType;


public class GeneralSettings extends AbstractEntity {

    private GeneralSettingType type;
    private String value;

    public GeneralSettings(GeneralSettingType type, Object value) {
        this.type = type;
        this.value = type.serialize(value);
    }

    @QueryProjection
    public GeneralSettings(String name, String value) {
        this.type = GeneralSettingType.valueOf(name);
        this.value = value;
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