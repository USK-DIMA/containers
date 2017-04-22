package ru.uskov.dmitry.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dmitry on 18.03.2017.
 */
abstract public class AbstractEntity implements Serializable {

    private Map<String, Object> additionalFields = new HashMap<>();

    public void addField(String key, Object value) {
        additionalFields.put(key, value);
    }

    public Object getValue(String key) {
        return additionalFields.get(key);
    }
}
