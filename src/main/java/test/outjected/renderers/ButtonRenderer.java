package test.outjected.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import test.outjected.components.Famlies;

@FacesRenderer(componentFamily = Famlies.OUTPUT_COMPONENT_FAMILY, rendererType = ButtonRenderer.RENDERER_TYPE)
public class ButtonRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "test.outjected.renderers.ButtonRenderer";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("button", component);
        writeStandardAttributes(context, component);
        writeAttributeIfExistsOrDefault("type", "type", "button", context, component);
        String value = (String) component.getAttributes().get("value");
        if (value != null) {
            writer.startElement("span", component);
            writer.write(value);
            writer.endElement("span");
        }
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("button");
    }
}
