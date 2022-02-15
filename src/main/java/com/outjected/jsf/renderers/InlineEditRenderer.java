package com.outjected.jsf.renderers;

import com.outjected.jsf.components.Families;
import com.outjected.jsf.utils.RendererTools;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;

@SuppressWarnings("resource") @FacesRenderer(componentFamily = Families.OUTPUT_COMPONENT_FAMILY, rendererType = InlineEditRenderer.RENDERER_TYPE) public class InlineEditRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.InlineEditRenderer";

    @Override public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        final boolean editable = RendererTools.attributeValueAsBoolean(component.getAttributes().get("editable"), true);
        if (editable) {
            writer.startElement("a", component);
            writeStandardAttributes(context, component);
            final UIComponent modalToToggle = (UIComponent) component.getAttributes().get("modal");

            String styleClass = (String) component.getAttributes().getOrDefault("styleClass", "");
            String styleClassValue = RendererTools.spaceSeperateStrings("inline-edit", styleClass);
            writeAttribute("class", styleClassValue, context);

            if (modalToToggle != null) {
                final String id = modalToToggle.getClientId().replace(":", "\\:");
                writeAttribute("data-bs-toggle", "modal", context);
                writeAttribute("data-bs-target", "#" + id, context);
            }
        }
    }

    @Override public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        final boolean editable = RendererTools.attributeValueAsBoolean(component.getAttributes().get("editable"), true);
        final ResponseWriter writer = context.getResponseWriter();
        if (editable) {
            writer.endElement("a");
        }
    }
}
