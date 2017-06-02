package ru.uskov.dmitry.enums;

/**
 * Created by Dmitry on 02.06.2017.
 */
public enum GeneralSettingType {

    UPDATE_TIME("5000", Object::toString, Long::valueOf);

    private String defaultValue;
    private Serializer serializer;
    private Parser parser;

    GeneralSettingType(String defaultValue, Serializer serializer, Parser parser) {
        this.defaultValue = defaultValue;
        this.serializer = serializer;
        this.parser = parser;
    }

    public Object parse(String value) {
        if (value == null) {
            value = defaultValue;
        }
        return parser.parse(value);
    }

    public String serialize(Object value) {
        return serializer.serialize(value);
    }

    public Object getDefault() {
        return parse(null);
    }

    @FunctionalInterface
    private interface Serializer {
        String serialize(Object objectValue);
    }

    @FunctionalInterface
    private interface Parser {
        Object parse(String objectValue);
    }
}
