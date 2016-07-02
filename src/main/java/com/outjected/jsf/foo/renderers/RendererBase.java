package com.outjected.jsf.foo.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

public class RendererBase extends Renderer {

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        super.encodeBegin(context, component);
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        super.encodeEnd(context, component);
    }

    public void writeAttributeIfExists(String attributeName, String name, FacesContext context, UIComponent component) throws IOException {
        String value = (String) component.getAttributes().get(attributeName);
        if (value != null && value.length() > 0) {
            context.getResponseWriter().writeAttribute(name, value, attributeName);
        }
    }

    public void writeAttributeIfExistsOrDefault(String attributeName, String name, String defaultValue, FacesContext context, UIComponent component) throws IOException {
        String value = (String) component.getAttributes().get(attributeName);
        if (value == null || value.length() == 0) {
            value = defaultValue;
        }
        context.getResponseWriter().writeAttribute(name, value, attributeName);
    }
}
