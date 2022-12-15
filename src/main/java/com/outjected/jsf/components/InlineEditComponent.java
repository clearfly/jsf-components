package com.outjected.jsf.components;

import java.io.IOException;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;

import com.outjected.jsf.utils.RendererTools;

@FacesComponent(value = "com.outjected.jsf.components.InlineEditComponent", namespace = Families.NAMESPACE)
public class InlineEditComponent extends ComponentBase {

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
            writer.startElement("a", this);
            writeStandardAttributes(context);
            final UIComponent modalToToggle = (UIComponent) getAttributes().get("modal");

            String styleClass = (String) getAttributes().getOrDefault("styleClass", "");
            String styleClassValue = RendererTools.spaceSeperateStrings("inline-edit", styleClass);
            writeAttribute("class", styleClassValue, context);

            if (modalToToggle != null) {
                final String id = modalToToggle.getClientId().replace(":", "\\:");
                writeAttribute("data-bs-toggle", "modal", context);
                writeAttribute("data-bs-target", "#" + id, context);
            }
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
