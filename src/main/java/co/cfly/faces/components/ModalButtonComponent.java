package co.cfly.faces.components;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;

@FacesComponent(value = "co.cfly.faces.components.ModalButtonComponent", namespace = Families.NAMESPACE)
public class ModalButtonComponent extends ComponentBase {

    static final Logger log = Logger.getLogger(ModalButtonComponent.class.getName());

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

        final Object modalAttributeObject = getAttributes().get("modal");
        if (modalAttributeObject instanceof UIComponent modalComponent) {
            writeAttribute("data-bs-toggle", "modal", context);
            writeAttribute("data-bs-target", "#" + modalComponent.getClientId(), context);
        }
        else {
            log.info("On %s the modalButton component %s attribute 'modal' must reference a UIComponent. Was: %s".formatted(context.getViewRoot().getViewId(), getClientId(),
                    Optional.ofNullable(modalAttributeObject).map(Objects::toString).orElse("Unset")));
        }

        final String value = (String) getAttributes().get("value");
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
