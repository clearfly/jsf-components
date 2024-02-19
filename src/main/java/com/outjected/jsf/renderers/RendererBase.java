package com.outjected.jsf.renderers;

import java.io.IOException;
import java.util.Objects;

import com.sun.faces.renderkit.html_basic.HtmlBasicRenderer;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;


public abstract class RendererBase extends HtmlBasicRenderer {

    @Override
    protected void setSubmittedValue(UIComponent component, Object newValue) {
        if (component instanceof final UIInput input) {
            if (Objects.nonNull(newValue)) {
                input.setSubmittedValue(newValue);
            }
        }
    }

    public void writeId(FacesContext context, UIComponent component) throws IOException {
        writeAttributeIfExists("clientId", "id", context, component);
    }

    public void writeAttribute(String name, String value, FacesContext context) throws IOException {
        context.getResponseWriter().writeAttribute(name, value, null);
    }

    public void writeAttributeIfExists(String attributeName, String name, FacesContext context, UIComponent component) throws IOException {
        String value = (String) component.getAttributes().get(attributeName);
        if (value != null && value.length() > 0) {
            context.getResponseWriter().writeAttribute(name, value, attributeName);
        }
    }
}
