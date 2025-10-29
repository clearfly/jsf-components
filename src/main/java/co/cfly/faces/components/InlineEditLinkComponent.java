package co.cfly.faces.components;

import java.io.IOException;

import co.cfly.faces.utils.RendererTools;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;

@FacesComponent(value = "co.cfly.faces.components.InlineEditLinkComponent", namespace = Families.NAMESPACE)
public class InlineEditLinkComponent extends ComponentBase{

    @Override
    public String getFamily() {
        return Families.OUTPUT_COMPONENT_FAMILY;
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException{
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("a", this);
        writeId(context);
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

        if (getAttributes().get("modal") instanceof UIComponent modalComponent) {
            writeAttribute("data-bs-toggle", "modal", context);
            writeAttribute("data-bs-target", "#" + modalComponent.getClientId(), context);
        }
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        final boolean editable = getAttribute("editable", true);
        final ResponseWriter writer = context.getResponseWriter();
        if (editable) {
            writer.endElement("a");
        }
    }
}
