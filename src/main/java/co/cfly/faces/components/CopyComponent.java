package co.cfly.faces.components;

import java.io.IOException;
import java.util.Objects;

import co.cfly.faces.utils.RendererTools;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;

@FacesComponent(value = "co.cfly.faces.components.CopyComponent", namespace = Families.NAMESPACE)
public class CopyComponent extends ComponentBase {

    @Override
    public String getFamily() {
        return Families.OUTPUT_COMPONENT_FAMILY;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        Object objectValue = getAttributes().get("value");
        boolean hidden = getAttribute("hidden", false);
        if (Objects.nonNull(objectValue)) {
            final String value;
            if (objectValue instanceof String) {
                value = (String) objectValue;
            }
            else {
                value = objectValue.toString();
            }

            ResponseWriter writer = context.getResponseWriter();
            writer.startElement("a", this);
            writer.writeAttribute("role", "button", "role");
            writer.writeAttribute("href", "javascript:void(0)", "href");
            writer.writeAttribute("aria-label", "Copy to clipboard", "aria-label");
            writer.writeAttribute("title", "Copy to clipboard", "title");
            writeId(context);
            String styleClassValue = RendererTools.spaceSeperateStrings("clipboard-copy-element", (String) getAttributes().get("styleClass"));
            writeAttribute("class", styleClassValue, context);
            writeAttributeIfExists("style", "style", context);
            writeAttribute("data-clipboard-text", value, context);
            writeAttribute("data-clipboard-action", "copy", context);

            if (hidden) {
                writeAttribute("hidden", "hidden", context);
            }

            writer.startElement("i", this);
            writer.writeAttribute("class", "far fa-copy", "class");
            writer.endElement("i");
            writer.endElement("a");
        }
    }
}
