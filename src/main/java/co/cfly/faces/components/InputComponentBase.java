package co.cfly.faces.components;

import java.io.IOException;

import co.cfly.faces.utils.RendererTools;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;

public abstract class InputComponentBase extends UIInput {

    public void writeId(FacesContext context) throws IOException {
        writeAttributeIfExists("clientId", "id", context);
    }

    public void writeAttributeIfExists(String attributeName, String name, FacesContext context) throws IOException {
        String value = (String) getAttributes().get(attributeName);
        if (value != null && !value.isEmpty()) {
            context.getResponseWriter().writeAttribute(name, value, attributeName);
        }
    }

    public void writeAttribute(String name, String value) throws IOException {
        this.getFacesContext().getResponseWriter().writeAttribute(name, value, null);
    }

    public boolean getAttribute(String name, boolean defaultValue) {
        return RendererTools.attributeValueAsBoolean(getAttributes().get(name), defaultValue);
    }

    public String getAttribute(String name, String defaultValue) {
        return (String) getAttributes().getOrDefault(name, defaultValue);
    }
}
