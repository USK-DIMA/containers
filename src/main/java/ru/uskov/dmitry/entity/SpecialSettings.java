package ru.uskov.dmitry.entity;

import com.querydsl.core.annotations.QueryProjection;
import ru.uskov.dmitry.enums.SpecialSettingsType;

/**
 * Created by Dmitry on 02.06.2017.
 */

public class SpecialSettings extends AbstractEntity {

    private SpecialSettingsType type;
    private String value;

    public SpecialSettings(SpecialSettingsType type, Object coordinate) {
        this.type = type;
        this.value = type.serialize(coordinate);
    }

    @QueryProjection
    public SpecialSettings(String name, String value) {
        this.type = SpecialSettingsType.valueOf(name);
        this.value = value;
    }



    public SpecialSettings() {
    }

    public SpecialSettingsType getType() {
        return type;
    }

    public void setType(SpecialSettingsType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}