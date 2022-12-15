package com.outjected.jsf.components;

import java.io.IOException;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;

import com.outjected.jsf.utils.RendererTools;

@FacesComponent(value = "com.outjected.jsf.components.DecorateOutputComponent", namespace = Families.NAMESPACE)
public class DecorateOutputComponent extends ComponentBase {

    @Override
    public String getFamily() {
        return Families.OUTPUT_COMPONENT_FAMILY;
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        final String label = (String) getAttributes().get("label");

        // Write Outer Div
        final String style = (String) getAttributes().get("style");
        final String styleClass = (String) getAttributes().get("styleClass");
        final String divComputedStyleClass = RendererTools.spaceSeperateStrings("o-decorate-output form-group", styleClass);

        final String labelClass = (String) getAttributes().get("labelClass");
        final String help = (String) getAttributes().get("help");
        final String valueClass = (String) getAttributes().get("valueClass");
        writer.startElement("div", this); // Outer Div
        writeId(context);
        writeAttribute("class", divComputedStyleClass, context);
        writeAttribute("style", style, context);

        final String clientId = getClientId();

        // Write Label
        final String labelComputedStyleClass = RendererTools.spaceSeperateStrings("col-form-label", labelClass);
        writer.startElement("label", this); // Label
        writeAttribute("for", clientId, context);
        writeAttribute("class", labelComputedStyleClass, context);
        writer.startElement("span", this);
        if (help != null) {
            writeAttribute("class", "popover-source", context);
            writeAttribute("data-bs-toggle", "popover", context);
            writeAttributeIfExists("helpContainer", "data-bs-container", context);
            writeAttributeIfExists("help", "data-bs-content", context);
            writeAttributeIfExistsOrDefault("helpContainer", "data-bs-container", "body", context);
            writeAttributeIfExistsOrDefault("helpPlacement", "data-bs-placement", "right", context);
            writeAttributeIfExistsOrDefault("helpTrigger", "data-bs-trigger", "hover", context);
            writeAttributeIfExistsOrDefault("helpDelay", "data-bs-delay", "0", context);
            writeAttributeIfExistsOrDefault("helpHtml", "data-bs-html", "true", context);
        }
        writer.write(label);
        writer.endElement("span");
        writer.endElement("label");

        // Write Value Div
        writer.startElement("div", this); // Value Div
        writeAttribute("class", RendererTools.spaceSeperateStrings("form-control-plaintext", valueClass), context);
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("div"); // Value Div
        writer.endElement("div"); // Outer Div
    }
}
