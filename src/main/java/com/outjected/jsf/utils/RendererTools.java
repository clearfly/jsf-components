package com.outjected.jsf.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class RendererTools {

    public static String spaceSeperateStrings(String... values) {
        if (values.length == 1) {
            return values[0];
        }
        else {
            return Arrays.stream(values).filter(Objects::nonNull).distinct().collect(Collectors.joining(" "));
        }
    }

    public static Long asLong(Object o) {
        if (o instanceof Number) {
            return ((Number) o).longValue();
        }
        else {
            return null;
        }
    }
}
