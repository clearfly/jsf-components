package com.outjected.jsf.renderers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.components.Famlies;
import com.outjected.jsf.components.TabComponent;
import com.outjected.jsf.utils.RendererTools;

@SuppressWarnings("resource")
@FacesRenderer(componentFamily = Famlies.OUTPUT_COMPONENT_FAMILY, rendererType = TabsRenderer.RENDERER_TYPE)
public class TabsRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.TabsRenderer";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        List<TabComponent> tabs = new ArrayList<>();

        for (UIComponent child : component.getChildren()) {
            if (child instanceof TabComponent && child.isRendered()) {
                tabs.add((TabComponent) child);
            }
        }

        // Write Outer Div
        final String style = (String) component.getAttributes().get("style");
        final String styleClass = (String) component.getAttributes().get("styleClass");
        final String divComputedStyleClass = RendererTools.spaceSeperateStrings("tabs", styleClass);
        writer.startElement("div", component); // Outer Div
        writeId(context, component);
        writeAttribute("class", divComputedStyleClass, context);
        writeAttribute("style", style, context);

        writer.startElement("ul", component);
        writeAttribute("id", "tabs", context);
        writeAttribute("class", "nav nav-pills", context);
        writeAttribute("role", "tablist", context);

        for (TabComponent tab : tabs) {
            // Write NAV Pills
            final String hash = (String) tab.getAttributes().get("hash");
            final String title = (String) tab.getAttributes().get("title");
            final Long count = RendererTools.asLong(tab.getAttributes().get("count"));
            writer.startElement("li", component);
            writeAttribute("role", "presentation", context);
            writer.startElement("a", component);
            writeAttribute("href", "#" + hash, context);
            writeAttribute("aria-controls", hash, context);
            writeAttribute("role", "tab", context);
            writeAttribute("data-toggle", "tab", context);
            writer.write(title);
            if (count != null && count > 0) {
                writer.startElement("span", component);
                writeAttribute("class", "badge", context);
                writer.write(count.toString());
                writer.endElement("span");
            }
            writer.endElement("a");
            writer.endElement("li");
        }
        writer.endElement("ul"); // End Pills

        writer.startElement("div", component); // Tab Content DIV

        writeAttribute("class", "tab-content", context);

        for (UIComponent child : component.getChildren()) {
            if (child instanceof TabComponent) {
                TabComponent tab = (TabComponent) child;
                final String hash = (String) tab.getAttributes().get("hash");
                writer.startElement("div", component);
                writeAttribute("role", "tabpanel", context);
                writeAttribute("class", "tab-pane", context);
                writeAttribute("id", hash, context);
                for (UIComponent c : tab.getChildren()) {
                    c.encodeAll(context);
                }
                writer.endElement("div");
            }
            else {
                child.encodeAll(context);
            }
        }
        writer.endElement("div"); // Tab Content DIV
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
