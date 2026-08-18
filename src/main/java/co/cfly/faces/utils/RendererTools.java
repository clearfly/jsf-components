package co.cfly.faces.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class RendererTools {

    public static String spaceSeparateStrings(String... values) {
        if (values.length == 1) {
            return values[0];
        }
        else {
            return Arrays.stream(values).filter(Objects::nonNull).distinct().collect(Collectors.joining(" "));
        }
    }

    public static Long asLong(Object o) {
        if (o instanceof Number n) {
            return n.longValue();
        }
        else {
            return null;
        }
    }

    public static boolean attributeValueAsBoolean(Object attributeValue, boolean defaultValue) {
        if (attributeValue instanceof String stringValue) {
            return Boolean.parseBoolean(stringValue);
        }
        else if (attributeValue instanceof Boolean booleanValue) {
            return booleanValue;
        }
        else {
            return defaultValue;
        }
    }
}
