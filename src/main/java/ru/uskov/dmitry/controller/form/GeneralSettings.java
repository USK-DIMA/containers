package ru.uskov.dmitry.controller.form;

import javax.persistence.Entity;

/**
 * Created by Dmitry on 02.06.2017.
 */
@Entity
public class GeneralSettings {

    private Long updateTimeout;

    public Long getUpdateTimeout() {
        return updateTimeout;
    }

    public void setUpdateTimeout(Long updateTimeout) {
        this.updateTimeout = updateTimeout;
    }
}