package test.outjected.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

public abstract class RendererBase extends Renderer {

    @Override
    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);
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

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        super.encodeChildren(context, component);
    }

    public void writeStandardAttributes(FacesContext context, UIComponent component) throws IOException {
        writeId(context, component);
        writeAttributeIfExists("style", "style", context, component);
        writeAttributeIfExists("styleClass", "class", context, component);
        writeAttributeIfExists("onclick", "onclick", context, component);
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
