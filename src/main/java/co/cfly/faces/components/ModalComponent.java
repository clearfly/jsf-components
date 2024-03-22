package co.cfly.faces.components;

import java.io.IOException;
import java.util.Objects;

import co.cfly.faces.utils.RendererTools;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;

@FacesComponent(value = "co.cfly.faces.components.ModalComponent", namespace = Families.NAMESPACE)
public class ModalComponent extends ComponentBase {

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

        final String maxWidth = (String) getAttributes().get("maxWidth");
        final String size = (String) getAttributes().getOrDefault("size", "lg");
        final String header = (String) getAttributes().get("header");
        final String styleClass = (String) getAttributes().get("styleClass");
        final String computedStyleClass = RendererTools.spaceSeperateStrings("modal fade", styleClass);

        ResponseWriter writer = context.getResponseWriter();

        // Outer Div
        writer.startElement("div", this);
        writeId(context);
        writer.writeAttribute("class", computedStyleClass, null);
        writeStyle(context);
        writeAttributeIfExistsOrDefault("keyboard", "data-bs-keyboard", "false", context);
        writeAttributeIfExistsOrDefault("backdrop", "data-bs-backdrop", "static", context);

        // Modal Dialog Div
        writer.startElement("div", this);

        switch (size) {
            case ("xl") -> writer.writeAttribute("class", "modal-dialog modal-xl", null);
            case ("lg") -> writer.writeAttribute("class", "modal-dialog modal-lg", null);
            case ("sm") -> writer.writeAttribute("class", "modal-dialog modal-sm", null);
            case ("std") -> writer.writeAttribute("class", "modal-dialog", null);
            default -> throw new IllegalArgumentException("Unsupported modal size definition: %s".formatted(size));
        }

        if (Objects.nonNull(maxWidth)) {
            writer.writeAttribute("style", "max-width:" + maxWidth + "px;", null);
        }

        // Modal Content Div
        writer.startElement("div", this);
        writer.writeAttribute("class", "modal-content", null);

        if (Objects.nonNull(header)) {
            writer.startElement("div", this);
            writer.writeAttribute("class", "modal-header", null);
            writer.startElement("h6", this);
            writer.writeAttribute("class", "modal-title", null);
            writer.write(header);
            writer.endElement("h6");
            writer.startElement("button", this);
            writer.writeAttribute("class", "btn-close", null);
            writer.writeAttribute("type", "button", null);
            writer.writeAttribute("data-bs-dismiss", "modal", null);
            writer.writeAttribute("aria-hidden", "true", null);
            writer.endElement("button");
            writer.endElement("div");// End Modal Header Div
        }

        // Modal Body Div
        writer.startElement("div", this);
        writer.writeAttribute("class", "modal-body", null);
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        writer.endElement("div"); // End Modal Body Div
        writer.endElement("div"); // End Modal Content Div
        writer.endElement("div"); // End Modal Dialog Div
        writer.endElement("div"); //End Modal Div
    }
}
