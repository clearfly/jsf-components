package com.outjected.jsf.renderers;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.components.Famlies;
import com.outjected.jsf.core.Notices;

@FacesRenderer(componentFamily = Famlies.OUTPUT_COMPONENT_FAMILY, rendererType = NoticesRenderer.RENDERER_TYPE)
public class NoticesRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.NoticesRenderer";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        final Notices notices = (Notices) component.getAttributes().get("notices");
        final String errorTitle = (String) component.getAttributes().getOrDefault("errorTitle", "Has Errors -- Click for Details");
        final String warningTitle = (String) component.getAttributes().getOrDefault("warningTitle", "Has Warnings -- Click for Details");
        final boolean expanded = (Boolean) component.getAttributes().getOrDefault("expanded", true);

        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("div", component);
        writeId(context, component);
        if (notices.isContainsNotices()) {
            writer.startElement("div", component);
            writer.writeAttribute("onclick", "$('#noticesContentDiv').slideToggle('slow'); $('#noticesTitleDiv').slideToggle('slow');", null);

            // Title Div
            writer.startElement("div", component);
            writer.writeAttribute("id", "noticesTitleDiv", null);
            writer.writeAttribute("class", notices.containsErrors() ? "alert alert-danger" : "alert alert-warning", null);
            writer.writeAttribute("style", "display:" + (expanded ? "none" : "block") + ";", null);
            writer.write(notices.containsErrors() ? errorTitle : warningTitle);
            writer.endElement("div"); // End Title Div

            // Content Div
            writer.startElement("div", component);
            writer.writeAttribute("id", "noticesContentDiv", null);
            writer.writeAttribute("style", "display:" + (expanded ? "block" : "none") + ";", null);

            writeSection(context, component, notices.getErrors(), "alert alert-danger");
            writeSection(context, component, notices.getWarnings(), "alert alert-warning");
            writer.endElement("div"); // End Content Div
        }
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("div");
    }

    private void writeSection(FacesContext context, UIComponent component, List<String> messages, String styleClass) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        if (messages != null && messages.size() > 0) {
            writer.startElement("div", component);
            writer.writeAttribute("class", styleClass, null);
            writer.startElement("ul", component);

            for (String message : messages) {
                writer.startElement("li", component);
                writer.write(message);
                writer.endElement("li");
            }

            writer.endElement("ul");
            writer.endElement("div");
        }
    }
}
