package com.outjected.jsf.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.components.Famlies;
import com.outjected.jsf.utils.RendererTools;

@SuppressWarnings("resource")
@FacesRenderer(componentFamily = Famlies.OUTPUT_COMPONENT_FAMILY, rendererType = PopoverRenderer.RENDERER_TYPE)
public class PopoverRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.PopoverRenderer";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("span", component);
        writeId(context, component);
        writeAttribute("data-toggle", "popover", context);
        final String computedStyleClass = RendererTools.spaceSeperateStrings("popover-source", (String) component.getAttributes().get("styleClass"));
        writeAttribute("class", (computedStyleClass), context);
        writeAttributeIfExists("style", "style", context, component);
        writeStandardAttributes(context, component);
        writeAttributeIfExistsOrDefault("placement", "data-placement", "right", context, component);
        writeAttributeIfExistsOrDefault("trigger", "data-trigger", "hover", context, component);
        writeAttributeIfExistsOrDefault("html", "data-html", "true", context, component);
        writeAttributeIfExistsOrDefault("delay", "data-delay", "0", context, component);
        writeAttributeIfExists("content", "data-content", context, component);
        writeAttributeIfExistsOrDefault("container", "data-container", "false", context, component);
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("span");
    }
}
