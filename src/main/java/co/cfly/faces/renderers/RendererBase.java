package co.cfly.faces.renderers;

import java.io.IOException;

import com.sun.faces.renderkit.html_basic.HtmlBasicInputRenderer;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;

public abstract class RendererBase extends HtmlBasicInputRenderer {

    public void writeId(FacesContext context, UIComponent component) throws IOException {
        writeAttributeIfExists("clientId", "id", context, component);
    }

    public void writeAttribute(String name, String value, FacesContext context) throws IOException {
        context.getResponseWriter().writeAttribute(name, value, null);
    }

    public void writeAttributeIfExists(String attributeName, String name, FacesContext context, UIComponent component) throws IOException {
        String value = (String) component.getAttributes().get(attributeName);
        if (value != null && !value.isEmpty()) {
            context.getResponseWriter().writeAttribute(name, value, attributeName);
        }
    }
}
