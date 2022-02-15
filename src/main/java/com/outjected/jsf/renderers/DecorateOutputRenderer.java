package com.outjected.jsf.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.components.Families;
import com.outjected.jsf.utils.RendererTools;

@SuppressWarnings("resource")
@FacesRenderer(componentFamily = Families.OUTPUT_COMPONENT_FAMILY, rendererType = DecorateOutputRenderer.RENDERER_TYPE)
public class DecorateOutputRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.DecorateOutputRenderer";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        final String label = (String) component.getAttributes().get("label");

        // Write Outer Div
        final String style = (String) component.getAttributes().get("style");
        final String styleClass = (String) component.getAttributes().get("styleClass");
        final String divComputedStyleClass = RendererTools.spaceSeperateStrings("o-decorate-output form-group", styleClass);

        final String labelClass = (String) component.getAttributes().get("labelClass");
        final String help = (String) component.getAttributes().get("help");
        final String valueClass = (String) component.getAttributes().get("valueClass");
        writer.startElement("div", component); // Outer Div
        writeId(context, component);
        writeAttribute("class", divComputedStyleClass, context);
        writeAttribute("style", style, context);

        final String clientId = component.getClientId();

        // Write Label
        final String labelComputedStyleClass = RendererTools.spaceSeperateStrings("col-form-label", labelClass);
        writer.startElement("label", component); // Label
        writeAttribute("for", clientId, context);
        writeAttribute("class", labelComputedStyleClass, context);
        writer.startElement("span", component);
        if (help != null) {
            writeAttribute("class", "popover-source", context);
            writeAttribute("data-bs-toggle", "popover", context);
            writeAttributeIfExists("helpContainer", "data-bs-container", context, component);
            writeAttributeIfExists("help", "data-bs-content", context, component);
            writeAttributeIfExistsOrDefault("helpContainer", "data-bs-container", "body", context, component);
            writeAttributeIfExistsOrDefault("helpPlacement", "data-bs-placement", "right", context, component);
            writeAttributeIfExistsOrDefault("helpTrigger", "data-bs-trigger", "hover", context, component);
            writeAttributeIfExistsOrDefault("helpDelay", "data-bs-delay", "0", context, component);
            writeAttributeIfExistsOrDefault("helpHtml", "data-bs-html", "true", context, component);
        }
        writer.write(label);
        writer.endElement("span");
        writer.endElement("label");

        // Write Value Div
        writer.startElement("div", component); // Value Div
        writeAttribute("class", RendererTools.spaceSeperateStrings("form-control-plaintext", valueClass), context);
        encodeValue(context, component);

        writer.endElement("div"); // Value Div
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) {
        // Children are rendered manually in the encodeBegin so we don't want to render them twice
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("div"); // Outer Div
    }

    private void encodeValue(FacesContext facesContext, UIComponent component) throws IOException {
        for (UIComponent c : component.getChildren()) {
            c.encodeAll(facesContext);
        }
    }
}
