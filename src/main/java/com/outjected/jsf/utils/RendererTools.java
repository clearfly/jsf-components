package com.outjected.jsf.utils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;

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

    public static UIForm parentForm(final UIComponent component) {
        UIComponent currentComponent = component;
        while (currentComponent != null) {
            if (currentComponent.getNamingContainer() instanceof UIForm) {
                return (UIForm) currentComponent.getNamingContainer();
            }
            else {
                currentComponent = currentComponent.getParent();
            }
        }
        throw new IllegalStateException("No parent form found for " + component.getClientId());
    }

    public static boolean horzontalLayout(UIForm parentForm) {
        String styleClass = (String) parentForm.getAttributes().get("styleClass");
        return styleClass != null && styleClass.contains("form-horizontal");
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
