package com.outjected.jsf.foo.utils;

public class RendererTools {

    public static String spaceSeperateStrings(String... values) {
        if (values.length == 1) {
            return values[0];
        }
        else {
            StringBuilder sb = new StringBuilder();
            for (String s : values) {
                if (s != null) {
                    if (sb.length() != 0) {
                        sb.append(" ");
                    }
                    sb.append(s);
                }
            }
            return sb.toString();
        }
    }
}
