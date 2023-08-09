package com.outjected.jsf.components;

import java.io.IOException;
import java.util.Objects;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

@FacesComponent(value = "com.outjected.jsf.components.ButtonComponent", namespace = Families.NAMESPACE)
public class ButtonComponent extends ComponentBase {

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
        writer.startElement("button", this);
        writeStandardAttributes(context);
        writeAttributeIfExistsOrDefault("type", "type", "button", context);
        String value = (String) getAttributes().get("value");

        if (getAttributes().get("modal") instanceof UIComponent modalComponent) {
            writeAttribute("data-bs-toggle", "modal", context);
            writeAttribute("data-bs-target", "#" + modalComponent.getClientId(), context);
        }

        if (Objects.nonNull(value)) {
            writer.startElement("span", this);
            writer.write(value);
            writer.endElement("span");
        }
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("button");
    }
}
