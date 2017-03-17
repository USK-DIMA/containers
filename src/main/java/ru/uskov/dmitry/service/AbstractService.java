package ru.uskov.dmitry.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dmitry on 11.03.2017.
 */
abstract public class AbstractService {

    protected FieldMap getMap(String field, Object value) {
        return new FieldMap(field, value);
    }

    protected Map<String, Object> getMapEnd(String field, Object value) {
        return new FieldMap(field, value).build();
    }


    /**
     * Билдер для мапа Map<String, Object>
     */
    protected static class FieldMap {

        private Map map;

        public FieldMap(String field, Object value) {
            put(field, value);
        }

        public FieldMap put(String field, Object value) {
            if (map == null) {
                map = new HashMap();
            }
            map.put(field, value);
            return this;
        }

        public Map<String, Object> end(String field, Object value) {
            if (map == null) {
                map = new HashMap();
            }
            map.put(field, value);
            return map;
        }

        public Map<String, Object> build() {
            return map;
        }
    }

}
