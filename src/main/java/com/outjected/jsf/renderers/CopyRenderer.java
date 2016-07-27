package com.outjected.jsf.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.components.Famlies;
import com.outjected.jsf.utils.RendererTools;

@SuppressWarnings("resource")
@FacesRenderer(componentFamily = Famlies.OUTPUT_COMPONENT_FAMILY, rendererType = CopyRenderer.RENDERER_TYPE)
public class CopyRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.CopyRenderer";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        Object objectValue = component.getAttributes().get("value");
        if (objectValue != null) {
            String value = null;
            if (objectValue instanceof String) {
                value = (String) objectValue;
            }
            else {
                value = objectValue.toString();
            }
            ResponseWriter writer = context.getResponseWriter();
            writer.startElement("button", component);
            writer.writeAttribute("type", "button", "type");
            writeId(context, component);
            String styleClass = (String) component.getAttributes().getOrDefault("styleClass", "btn btn-default btn-xs");
            String styleClassValue = RendererTools.spaceSeperateStrings("clipboard-copy-button", styleClass);
            writeAttribute("class", styleClassValue, context);
            writeAttributeIfExists("style", "style", context, component);
            writeAttribute("data-clipboard-text", value, context);
            writeAttribute("data-clipboard-action", "copy", context);

            writer.startElement("i", component);
            writer.writeAttribute("class", "fa fa-copy", "class");
            writer.endElement("i");
            writer.endElement("button");
        }
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
    }
}
