package com.outjected.jsf.components;

import java.io.IOException;

import jakarta.faces.component.UIComponentBase;
import jakarta.faces.context.FacesContext;

import com.outjected.jsf.utils.RendererTools;

public abstract class ComponentBase extends UIComponentBase {

    public void writeId(FacesContext context) throws IOException {
        writeAttributeIfExists("clientId", "id", context);
    }

    public void writeStyle(FacesContext context) throws IOException {
        writeAttributeIfExists("style", "style", context);
    }

    public void writeStandardAttributes(FacesContext context) throws IOException {
        writeId(context);
        writeAttributeIfExists("style", "style", context);
        writeAttributeIfExists("styleClass", "class", context);
        writeAttributeIfExists("onclick", "onclick", context);
        writeAttributeIfExists("title", "title", context);
    }

    public void writeAttribute(String name, String value, FacesContext context) throws IOException {
        context.getResponseWriter().writeAttribute(name, value, null);
    }

    public void writeAttributeIfExists(String attributeName, String name, FacesContext context) throws IOException {
        String value = (String) getAttributes().get(attributeName);
        if (value != null && value.length() > 0) {
            context.getResponseWriter().writeAttribute(name, value, attributeName);
        }
    }

    public void writeAttributeIfExistsOrDefault(String attributeName, String name, Object defaultValue, FacesContext context) throws IOException {
        Object value = getAttributes().getOrDefault(attributeName, defaultValue);
        context.getResponseWriter().writeAttribute(name, value, attributeName);
    }

    public boolean getAttribute(String name, boolean defaultValue) {
        return RendererTools.attributeValueAsBoolean(getAttributes().get(name), defaultValue);
    }
}
