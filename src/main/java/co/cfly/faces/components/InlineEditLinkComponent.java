package co.cfly.faces.components;

import java.io.IOException;

import co.cfly.faces.utils.RendererTools;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;

@FacesComponent(value = "co.cfly.faces.components.InlineEditLinkComponent", namespace = Families.NAMESPACE)
public class InlineEditLinkComponent extends ComponentBase {

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
        final ResponseWriter writer = context.getResponseWriter();
        final boolean editable = getAttribute("editable", true);
        if (editable) {
            writer.startElement("span", this);
            writeId(context);

            String styleClass = (String) getAttributes().getOrDefault("styleClass", "");
            String styleClassValue = RendererTools.spaceSeperateStrings("inline-edit popover-source", styleClass);
            writeAttribute("class", styleClassValue, context);

            writeAttributeIfExists("style", "style", context);
            writeStandardAttributes(context);

            // Popover attributes for JS initialization
            writeAttribute("data-bs-dual-toggle", "popover", context);
            writeAttributeIfExistsOrDefault("placement", "data-bs-placement", "right", context);
            writeAttributeIfExistsOrDefault("trigger", "data-bs-trigger", "hover", context);
            writeAttributeIfExistsOrDefault("html", "data-bs-html", "true", context);
            writeAttributeIfExistsOrDefault("delay", "data-bs-delay", "{\"show\":0,\"hide\":5000}", context);
            writeAttributeIfExistsOrDefault("container", "data-bs-container", "false", context);

            if (getAttributes().get("modal") instanceof UIComponent modalComponent) {
                writeAttribute("data-bs-toggle", "modal", context);
                writeAttribute("data-bs-target", "#" + modalComponent.getClientId(), context);
            }

            // Write label text
            String label = (String) getAttributes().getOrDefault("label", "");
            writer.writeText(label, "label");
        }
    }

    @Override
    public void encodeChildren(FacesContext context) throws IOException {
        final boolean editable = getAttribute("editable", true);
        if (editable && getChildCount() > 0) {
            final ResponseWriter writer = context.getResponseWriter();
            writer.startElement("div", this);
            writeAttribute("style", "display:none;", context); // Hidden by default
            for (UIComponent child : getChildren()) {
                child.encodeAll(context);
            }
            writer.endElement("div");
            //            writeAttributeIfExists(this, "data-bs-content", context);
        }
    }



    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        final boolean editable = getAttribute("editable", true);
        final ResponseWriter writer = context.getResponseWriter();
        if (editable) {
            writer.endElement("span");
        }
    }
}
