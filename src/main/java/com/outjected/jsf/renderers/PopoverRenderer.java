package com.outjected.jsf.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.components.Famlies;
import com.outjected.jsf.utils.RendererTools;

@FacesRenderer(componentFamily = Famlies.OUTPUT_COMPONENT_FAMILY, rendererType = PopoverRenderer.RENDERER_TYPE)
public class PopoverRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.PopoverRenderer";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("div", component);
        writeId(context, component);
        writeAttribute("styleClass", RendererTools.spaceSeperateStrings("popover-source", (String) component.getAttributes().get("styleClass")), context);
        writeAttributeIfExists("style", "style", context, component);
        writeStandardAttributes(context, component);
        writeAttributeIfExistsOrDefault("placement", "data-placement", "right", context, component);
        writeAttributeIfExistsOrDefault("trigger", "data-trigger", "click", context, component);
        writeAttributeIfExistsOrDefault("html", "data-html", "true", context, component);
        writeAttributeIfExistsOrDefault("delay", "data-delay", "0", context, component);
        writeAttributeIfExists("popoverTitle", "data-title", context, component);
        writeAttributeIfExistsOrDefault("container", "data-container", "false", context, component);
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("div");
    }
}
