package com.outjected.jsf.components;

import java.io.IOException;
import java.util.Objects;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;

import com.outjected.jsf.utils.RendererTools;

@FacesComponent(value = "com.outjected.jsf.components.ObjectFieldComponent", namespace = Families.NAMESPACE)
public class ObjectFieldComponent extends ComponentBase {

    @Override
    public String getFamily() {
        return Families.OUTPUT_COMPONENT_FAMILY;
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    @Override
    public boolean isRendered() {
        if (super.isRendered()) {
            final boolean hasChildrenToRender = getChildren().stream().anyMatch(UIComponent::isRendered);
            final boolean renderEmpty = getAttribute("renderEmpty", false);
            //Only Render if some of the children are set to render
            return hasChildrenToRender || renderEmpty;
        }
        else {
            //If explicitly set to not render then don't render
            return false;
        }
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        final String label = (String) getAttributes().get("label");

        // Write Outer Div
        final String style = (String) getAttributes().get("style");
        final String styleClass = (String) getAttributes().get("styleClass");
        final String divComputedStyleClass = RendererTools.spaceSeperateStrings("object-field row", styleClass);

        final String labelDivClass = (String) getAttributes().getOrDefault("labelClass", "col-5");
        final String help = (String) getAttributes().get("help");
        final String valueClass = (String) getAttributes().getOrDefault("valueClass", "col-7");
        writer.startElement("div", this); // Outer Div
        writeId(context);
        writeAttribute("class", divComputedStyleClass, context);
        writeAttribute("style", style, context);

        // Write Label
        writer.startElement("div", this); // Label div
        writeAttribute("class", RendererTools.spaceSeperateStrings("object-field-label", labelDivClass), context);
        writer.startElement("span", this); // Label span
        if (Objects.nonNull(help)) {
            writeAttribute("class", "popover-source label-content", context);
            writeAttribute("data-bs-toggle", "popover", context);
            writeAttributeIfExists("helpContainer", "data-bs-container", context);
            writeAttributeIfExists("help", "data-bs-content", context);
            writeAttributeIfExistsOrDefault("helpContainer", "data-bs-container", "body", context);
            writeAttributeIfExistsOrDefault("helpPlacement", "data-bs-placement", "right", context);
            writeAttributeIfExistsOrDefault("helpTrigger", "data-bs-trigger", "hover", context);
            writeAttributeIfExistsOrDefault("helpDelay", "data-bs-delay", "0", context);
            writeAttributeIfExistsOrDefault("helpHtml", "data-bs-html", "true", context);
        }
        else {
            writeAttribute("class", "label-content", context);
        }

        writer.write(label);
        writer.endElement("span"); //end label span
        writer.endElement("div"); //end label div

        // Write Value Div
        writer.startElement("div", this); // Value Div
        writeAttribute("class", RendererTools.spaceSeperateStrings("object-field-value", valueClass), context);
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        writer.endElement("div"); // Value Div
        writer.endElement("div"); // Outer Div
    }
}
