package com.outjected.jsf.components;

import java.io.IOException;
import java.util.Objects;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.outjected.jsf.utils.RendererTools;

@FacesComponent(value = "com.outjected.jsf.components.CopyComponent", namespace = Families.NAMESPACE)
public class CopyComponent extends ComponentBase {

    @Override
    public String getFamily() {
        return Families.OUTPUT_COMPONENT_FAMILY;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        Object objectValue = getAttributes().get("value");
        if (Objects.nonNull(objectValue)) {
            final String value;
            if (objectValue instanceof String) {
                value = (String) objectValue;
            }
            else {
                value = objectValue.toString();
            }
            ResponseWriter writer = context.getResponseWriter();
            writer.startElement("button", this);
            writer.writeAttribute("type", "button", "type");
            writeId(context);
            String styleClass = (String) getAttributes().getOrDefault("styleClass", "btn btn-outline-secondary btn-sm");
            String styleClassValue = RendererTools.spaceSeperateStrings("clipboard-copy-button", styleClass);
            writeAttribute("class", styleClassValue, context);
            writeAttributeIfExists("style", "style", context);
            writeAttribute("data-clipboard-text", value, context);
            writeAttribute("data-clipboard-action", "copy", context);

            writer.startElement("i", this);
            writer.writeAttribute("class", "far fa-copy", "class");
            writer.endElement("i");
            writer.endElement("button");
        }
    }
}
