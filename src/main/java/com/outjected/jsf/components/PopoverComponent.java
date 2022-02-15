package com.outjected.jsf.components;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.outjected.jsf.utils.RendererTools;

@FacesComponent(value = "com.outjected.jsf.components.PopoverComponent", namespace = Families.NAMESPACE)
public class PopoverComponent extends ComponentBase {

    @Override
    public String getFamily() {
        return Families.OUTPUT_COMPONENT_FAMILY;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("span", this);
        writeId(context);
        writeAttribute("data-bs-toggle", "popover", context);
        final String computedStyleClass = RendererTools.spaceSeperateStrings("popover-source", (String) getAttributes().get("styleClass"));
        writeAttribute("class", (computedStyleClass), context);
        writeAttributeIfExists("style", "style", context);
        writeStandardAttributes(context);
        writeAttributeIfExistsOrDefault("placement", "data-bs-placement", "right", context);
        writeAttributeIfExistsOrDefault("trigger", "data-bs-trigger", "hover", context);
        writeAttributeIfExistsOrDefault("html", "data-bs-html", "true", context);
        writeAttributeIfExistsOrDefault("delay", "data-bs-delay", "0", context);
        writeAttributeIfExists("content", "data-bs-content", context);
        writeAttributeIfExistsOrDefault("container", "data-bs-container", "false", context);
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("span");
    }
}
