package com.outjected.jsf.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.components.Famlies;
import com.outjected.jsf.utils.RendererTools;

@SuppressWarnings("resource")
@FacesRenderer(componentFamily = Famlies.OUTPUT_COMPONENT_FAMILY, rendererType = ObjectFieldRenderer.RENDERER_TYPE)
public class ObjectFieldRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.ObjectFieldRenderer";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        final boolean hasChildrenToRender = component.getChildren().stream().anyMatch(UIComponent::isRendered);
        final boolean renderEmpty = RendererTools.attributeValueAsBoolean(component.getAttributes().get("renderEmpty"), false);
        if (hasChildrenToRender || renderEmpty) {
            component.getAttributes().put(WRITE_CLOSING_KEY, true);
            final String label = (String) component.getAttributes().get("label");

            // Write Outer Div
            final String style = (String) component.getAttributes().get("style");
            final String styleClass = (String) component.getAttributes().get("styleClass");
            final String divComputedStyleClass = RendererTools.spaceSeperateStrings("object-field row", styleClass);

            final String labelClass = (String) component.getAttributes().getOrDefault("labelClass", "col-5");
            final String help = (String) component.getAttributes().get("help");
            final String valueClass = (String) component.getAttributes().getOrDefault("valueClass", "col-7");
            writer.startElement("div", component); // Outer Div
            writeId(context, component);
            writeAttribute("class", divComputedStyleClass, context);
            writeAttribute("style", style, context);

            final String clientId = component.getClientId();

            // Write Label
            final String labelComputedStyleClass = RendererTools.spaceSeperateStrings("object-field-label", labelClass);
            writer.startElement("label", component); // Label
            writeAttribute("for", clientId, context);
            writeAttribute("class", labelComputedStyleClass, context);
            writer.startElement("span", component);
            if (help != null) {
                writeAttribute("class", "popover-source", context);
                writeAttribute("data-toggle", "popover", context);
                writeAttributeIfExists("helpContainer", "data-container", context, component);
                writeAttributeIfExists("help", "data-content", context, component);
                writeAttributeIfExistsOrDefault("helpContainer", "data-container", "body", context, component);
                writeAttributeIfExistsOrDefault("helpPlacement", "data-placement", "right", context, component);
                writeAttributeIfExistsOrDefault("helpTrigger", "data-trigger", "hover", context, component);
                writeAttributeIfExistsOrDefault("helpDelay", "data-delay", "0", context, component);
                writeAttributeIfExistsOrDefault("helpHtml", "data-html", "true", context, component);
            }
            writer.write(label);
            writer.endElement("span");
            writer.endElement("label");

            // Write Value Div
            writer.startElement("div", component); // Value Div
            writeAttribute("class", RendererTools.spaceSeperateStrings("object-field-value", valueClass), context);
            encodeValue(context, component);

            writer.endElement("div"); // Value Div
        }
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) {
        // Children are rendered manually in the encodeBegin so we don't want to render them twice
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        final boolean writeClosingDiv = (boolean) component.getAttributes().getOrDefault(WRITE_CLOSING_KEY, false);
        if (writeClosingDiv) {
            ResponseWriter writer = context.getResponseWriter();
            writer.endElement("div"); // Outer Div
        }
    }

    private void encodeValue(FacesContext facesContext, UIComponent component) throws IOException {
        for (UIComponent c : component.getChildren()) {
            c.encodeAll(facesContext);
        }
    }
}
