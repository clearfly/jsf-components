package co.cfly.faces.components;

import java.io.IOException;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;

@FacesComponent(value= "co.cfly.faces.components.LinkComponent", namespace=Families.NAMESPACE)
public class LinkComponent extends ComponentBase {

    @Override
    public String getFamily() {return Families.OUTPUT_COMPONENT_FAMILY;}

    @Override
    public boolean getRendersChildren() {return true;}

    @Override
    public void encodeBegin(jakarta.faces.context.FacesContext context) throws java.io.IOException {
        jakarta.faces.context.ResponseWriter writer = context.getResponseWriter();
        writer.startElement("a", this);
        writeStandardAttributes(context);

        if (getAttributes().get("modal") instanceof UIComponent modalComponent) {
            writeAttribute("data-bs-toggle", "modal", context);
            writeAttribute("data-bs-target", "#" + modalComponent.getClientId(), context);
        }
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("a");
    }
}
