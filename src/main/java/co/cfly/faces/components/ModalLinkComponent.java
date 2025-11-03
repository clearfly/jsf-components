package co.cfly.faces.components;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;

@FacesComponent(value = "co.cfly.faces.components.ModalLinkComponent", namespace = Families.NAMESPACE)
public class ModalLinkComponent extends ComponentBase {

    static final Logger log = Logger.getLogger(ModalLinkComponent.class.getName());

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
        writer.startElement("a", this);
        writeStandardAttributes(context);
        writer.writeAttribute("href", "#", "href");
        writeAttributeIfExists("style", "style", context);
        writer.writeAttribute("title", "Open Modal", "title");

        final Object modalAttributeObject = getAttributes().get("modal");
        if (modalAttributeObject instanceof UIComponent modalComponent) {
            writeAttribute("data-bs-toggle", "modal", context);
            writeAttribute("data-bs-target", "#" + modalComponent.getClientId(), context);
        }
        else {
            log.info("The modalLink component %s attribute 'modal' must reference a UIComponent. Was: %s".formatted(getClientId(),
                    Optional.ofNullable(modalAttributeObject).map(Objects::toString).orElse("Unset")));
        }

        final String value = (String) getAttributes().get("value");
        if (Objects.nonNull(value)) {
            writer.write(value);
        }
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("a");
    }
}
