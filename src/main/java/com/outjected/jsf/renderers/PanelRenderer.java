package com.outjected.jsf.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.components.Famlies;
import com.outjected.jsf.utils.RendererTools;

@FacesRenderer(componentFamily = Famlies.OUTPUT_COMPONENT_FAMILY, rendererType = PanelRenderer.RENDERER_TYPE)
public class PanelRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.PanelRenderer";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        final UIComponent top = component.getFacet("top");
        final String header = (String) component.getAttributes().get("header");

        // Write Outer Div
        final String style = (String) component.getAttributes().get("style");
        final String styleClass = (String) component.getAttributes().get("styleClass");
        final String divComputedStyleClass = RendererTools.spaceSeperateStrings("panel panel-default", styleClass);
        writer.startElement("div", component); // Outer Div
        writeId(context, component);
        writeAttribute("class", divComputedStyleClass, context);
        writeAttributeIfExists("style", style, context, component);

        if (top != null && header == null) {
            writer.startElement("div", component);
            writer.writeAttribute("class", "panel-top", null);
            top.encodeAll(context);
            writer.endElement("div");
        }
        else if (header != null && top == null) {
            writer.startElement("div", component);
            writer.writeAttribute("class", "panel-top", null);
            writer.startElement("h5", component);
            writer.write(header);
            writer.endElement("h5");
            writer.endElement("div");
        }
        else {
            throw new IllegalArgumentException("Cannot define both a top facet and a header");
        }

        writer.startElement("div", component); //
        writer.writeAttribute("class", "panel-body", null);

        for (UIComponent child : component.getChildren()) {
            child.encodeAll(context);
        }
        writer.endElement("div");
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
}
