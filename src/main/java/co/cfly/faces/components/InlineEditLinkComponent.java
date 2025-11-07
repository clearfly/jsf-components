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
            writeAttributeIfExistsOrDefault("title", "data-bs-title", "test title", context);
            writeAttributeIfExistsOrDefault("placement", "data-bs-placement", "bottom", context);
            writeAttributeIfExistsOrDefault("html", "data-bs-html", "true", context);
            writeAttributeIfExistsOrDefault("delay", "data-bs-delay", "{\"show\":500,\"hide\":5000}", context);
            writeAttributeIfExistsOrDefault("container", "data-bs-container", "false", context);

            if (getAttributes().get("modal") instanceof UIComponent modalComponent) {
                writeAttribute("data-bs-toggle", "modal", context);
                writeAttribute("data-bs-target", "#" + modalComponent.getClientId(), context);
            }

            // Write label text
            String label = (String) getAttributes().getOrDefault("label", "test label");
            writer.writeText(label, "label");

            // Render children in a hidden template for popover content
            writer.startElement("template", this);
            writer.writeAttribute("id", getClientId(context) + "_popoverContent", null);
            for (UIComponent child : getChildren()) {
                child.encodeAll(context);
            }
            writer.endElement("template");

            // Reference the template in the popover content attribute
            writeAttribute("data-bs-content", "document.getElementById('" + getClientId(context) + "_popoverContent').innerHTML", context);
        }
    }

    @Override
    public void encodeChildren(FacesContext context) {
        // Since Children are rendered manually in the encodeBegin we don't want to render them twice
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
