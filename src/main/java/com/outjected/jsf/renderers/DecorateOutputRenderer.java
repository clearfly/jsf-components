package com.outjected.jsf.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.components.Famlies;
import com.outjected.jsf.utils.RendererTools;

@SuppressWarnings("resource")
@FacesRenderer(componentFamily = Famlies.OUTPUT_COMPONENT_FAMILY, rendererType = DecorateOutputRenderer.RENDERER_TYPE)
public class DecorateOutputRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.DecorateOutputRenderer";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        final String label = (String) component.getAttributes().get("label");
        final UIForm parentForm = RendererTools.parentForm(component);
        final boolean horizontalLayout = RendererTools.horzontalLayout(parentForm);

        // Write Outer Div
        final String style = (String) component.getAttributes().get("style");
        final String styleClass = (String) component.getAttributes().get("styleClass");
        final String divComputedStyleClass = RendererTools.spaceSeperateStrings("o-decorate-output form-group", styleClass);

        final String labelClass = (String) component.getAttributes().getOrDefault("labelClass", horizontalLayout ? "col-md-4" : null);
        final String help = (String) component.getAttributes().get("help");
        final String valueClass = (String) component.getAttributes().getOrDefault("valueClass", horizontalLayout ? "col-md-6" : null);
        writer.startElement("div", component); // Outer Div
        writeId(context, component);
        writeAttribute("class", divComputedStyleClass, context);
        writeAttribute("style", style, context);

        final String clientId = component.getClientId();

        // Write Label
        final String labelHelpClass = help != null ? "popover-source" : null;
        final String labelComputedStyleClass = RendererTools.spaceSeperateStrings("control-label", labelClass, labelHelpClass);
        writer.startElement("label", component); // Label
        writeAttribute("for", clientId, context);
        writeAttribute("title", label, context);
        writeAttribute("class", labelComputedStyleClass, context);
        if (help != null) {
            writeAttribute("data-toggle", "popover", context);
            writeAttributeIfExists("helpContainer", "data-container", context, component);
            writeAttributeIfExists("help", "data-content", context, component);
            writeAttributeIfExistsOrDefault("helpTitle", "data-title", label, context, component);
            writeAttributeIfExistsOrDefault("helpContainer", "data-container", "body", context, component);
            writeAttributeIfExistsOrDefault("helpPlacement", "data-placement", "right", context, component);
            writeAttributeIfExistsOrDefault("helpTrigger", "data-trigger", "click", context, component);
            writeAttributeIfExistsOrDefault("helpDelay", "data-delay", "0", context, component);
            writeAttributeIfExistsOrDefault("helpHtml", "data-html", "true", context, component);
        }
        writer.startElement("span", component);
        writer.write(label);
        writer.endElement("span");
        writer.endElement("label");

        // Write Value Div
        writer.startElement("div", component); // Value Div
        writeAttribute("class", RendererTools.spaceSeperateStrings("form-control-static", valueClass), context);
        encodeValue(context, component);

        writer.endElement("div"); // Value Div
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) {
        // Noop
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("div"); // Outer Div
    }

    private void encodeValue(FacesContext facesContext, UIComponent component) throws IOException {
        for (UIComponent c : component.getChildren()) {
            c.encodeAll(facesContext);
        }
    }
}
