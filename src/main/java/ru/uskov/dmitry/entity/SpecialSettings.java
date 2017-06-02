package ru.uskov.dmitry.entity;

import ru.uskov.dmitry.enums.SpecialSettingsType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * Created by Dmitry on 02.06.2017.
 */
@Entity
public class SpecialSettings extends AbstractEntity {

    @Id
    @Enumerated(EnumType.STRING)
    private SpecialSettingsType type;
    private String value;

    public SpecialSettings(SpecialSettingsType type, Object coordinate) {
        this.type = type;
        this.value = type.serialize(coordinate);
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