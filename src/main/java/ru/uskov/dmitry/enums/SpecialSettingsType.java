package ru.uskov.dmitry.enums;


/**
 * Created by Dmitry on 02.06.2017.
 */
public enum SpecialSettingsType {

    START_POINT_COORDINATE("55.76, 37.64", objectValue -> {
        Double[] value = (Double[]) objectValue;
        return value[0] + "," + value[1];
    }, strValue -> {
        Double[] value = new Double[2];
        String[] coodrdinates = strValue.split(",");
        value[0] = Double.parseDouble(coodrdinates[0]);
        value[1] = Double.parseDouble(coodrdinates[1]);
        return value;
    });

    private String defaultValue;
    private Serializer serializer;
    private Parser parser;

    SpecialSettingsType(String defaultValue, Serializer serializer, Parser parser) {
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