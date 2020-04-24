package com.outjected.jsf.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import com.sun.faces.renderkit.html_basic.HtmlBasicRenderer;

public abstract class RendererBase extends HtmlBasicRenderer {

    public static final String WRITE_CLOSING_KEY = "writeClosing";

    @Override
    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);
    }

    @Override
    protected void setSubmittedValue(UIComponent component, Object newValue) {
        if (component instanceof UIInput) {
            UIInput input = (UIInput) component;
            if (null != newValue) {
                input.setSubmittedValue(newValue);
            }
        }
    }

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        super.encodeBegin(context, component);
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        super.encodeEnd(context, component);
    }

    public void writeId(FacesContext context, UIComponent component) throws IOException {
        writeAttributeIfExists("clientId", "id", context, component);
    }

    public void writeStyle(FacesContext context, UIComponent component) throws IOException {
        writeAttributeIfExists("style", "style", context, component);

    }

    public void writeStyleClass(FacesContext context, UIComponent component) throws IOException {
        writeAttributeIfExists("styleClass", "class", context, component);
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        super.encodeChildren(context, component);
    }

    public void writeStandardAttributes(FacesContext context, UIComponent component) throws IOException {
        writeId(context, component);
        writeAttributeIfExists("style", "style", context, component);
        writeAttributeIfExists("styleClass", "class", context, component);
        writeAttributeIfExists("onclick", "onclick", context, component);
        writeAttributeIfExists("title", "title", context, component);
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

    public void writeAttributeIfExistsOrDefault(String attributeName, String name, Object defaultValue, FacesContext context, UIComponent component) throws IOException {
        Object value = component.getAttributes().getOrDefault(attributeName, defaultValue);
        context.getResponseWriter().writeAttribute(name, value, attributeName);
    }
}
