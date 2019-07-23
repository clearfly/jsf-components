package com.outjected.jsf.renderers;

import java.io.IOException;
import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.components.Famlies;
import com.outjected.jsf.utils.RendererTools;

@SuppressWarnings("resource")
@FacesRenderer(componentFamily = Famlies.OUTPUT_COMPONENT_FAMILY, rendererType = ModalRenderer.RENDERER_TYPE)
public class ModalRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.ModalRenderer";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {

        final String maxWidth = (String) component.getAttributes().get("maxWidth");
        final String size = (String) component.getAttributes().getOrDefault("size", "default");
        final String header = (String) component.getAttributes().get("header");
        final String styleClass = (String) component.getAttributes().get("styleClass");
        final String computedStyleClass = RendererTools.spaceSeperateStrings("modal fade", styleClass);

        ResponseWriter writer = context.getResponseWriter();

        // Outer Div
        writer.startElement("div", component);
        writeId(context, component);
        writer.writeAttribute("class", computedStyleClass, null);
        writeStyle(context, component);
        writeAttributeIfExistsOrDefault("keyboard", "data-keyboard", "false", context, component);
        writeAttributeIfExistsOrDefault("backdrop", "data-backdrop", "static", context, component);

        // Modal Dialog Div
        writer.startElement("div", component);

        switch (size) {
            case ("xl"):
                writer.writeAttribute("class", "modal-dialog modal-xl", null);
                break;
            case ("lg"):
                writer.writeAttribute("class", "modal-dialog modal-lg", null);
                break;
            case ("sm"):
                writer.writeAttribute("class", "modal-dialog modal-sm", null);
                break;
            default:
                writer.writeAttribute("class", "modal-dialog", null);
                break;
        }

        if (Objects.nonNull(maxWidth)) {
            writer.writeAttribute("style", "max-width:" + maxWidth + "px;", null);
        }

        // Modal Content Div
        writer.startElement("div", component);
        writer.writeAttribute("class", "modal-content", null);

        if (Objects.nonNull(header)) {
            writer.startElement("div", component);
            writer.writeAttribute("class", "modal-header", null);
            writer.startElement("h4", component);
            writer.writeAttribute("class", "modal-title", null);
            writer.write(header);
            writer.endElement("h4");
            writer.startElement("button", component);
            writer.writeAttribute("class", "close", null);
            writer.writeAttribute("type", "button", null);
            writer.writeAttribute("data-dismiss", "modal", null);
            writer.writeAttribute("aria-hidden", "true", null);
            writer.write(Character.toChars(215));
            writer.endElement("button");
            writer.endElement("div");// End Modal Header Div
        }
        // Modal Body Div
        writer.startElement("div", component);
        writer.writeAttribute("class", "modal-body", null);

        for (UIComponent c : component.getChildren()) {
            c.encodeAll(context);
        }
        writer.endElement("div"); // End Modal Body Div
        writer.endElement("div"); // End Modal Content Div
        writer.endElement("div"); // End Modal Dialog Div
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) {
        // Noop
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("div");
    }
}
