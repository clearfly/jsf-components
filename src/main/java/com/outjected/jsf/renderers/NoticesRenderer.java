package com.outjected.jsf.renderers;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.components.Families;
import com.outjected.jsf.core.Notices;
import com.outjected.jsf.utils.RendererTools;

@SuppressWarnings("resource")
@FacesRenderer(componentFamily = Families.OUTPUT_COMPONENT_FAMILY, rendererType = NoticesRenderer.RENDERER_TYPE)
public class NoticesRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.NoticesRenderer";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        final Notices notices = (Notices) component.getAttributes().get("notices");
        final String errorTitle = (String) component.getAttributes().getOrDefault("errorTitle", "Has Errors -- Click for Details");
        final String warningTitle = (String) component.getAttributes().getOrDefault("warningTitle", "Has Warnings -- Click for Details");
        final boolean expanded = RendererTools.attributeValueAsBoolean(component.getAttributes().get("expanded"), true);

        if (notices.isContainsNotices()) {
            component.getAttributes().put(WRITE_CLOSING_KEY, true);

            ResponseWriter writer = context.getResponseWriter();
            writer.startElement("div", component); //Start Outer div
            writeId(context, component);

            writer.startElement("div", component); // Start Content div
            writer.writeAttribute("onclick", "$('#noticesContentDiv').slideToggle('slow'); $('#noticesTitleDiv').slideToggle('slow');", null);

            // Title Div
            writer.startElement("div", component); //Start title div
            writer.writeAttribute("id", "noticesTitleDiv", null);
            writer.writeAttribute("class", notices.containsErrors() ? "alert alert-danger alert-thin text-center" : "alert alert-warning alert-thin text-center", null);
            writer.writeAttribute("style", "display:" + (expanded ? "none" : "block") + ";", null);
            writer.startElement("strong", component);
            writer.write(notices.containsErrors() ? errorTitle : warningTitle);
            writer.endElement("strong");
            writer.endElement("div"); // End Title Div

            writer.startElement("div", component);  // Start Sections div
            writer.writeAttribute("id", "noticesContentDiv", null);
            writer.writeAttribute("style", "display:" + (expanded ? "block" : "none") + ";", null);
            writeSection(context, component, notices.getErrors(), "alert alert-thin alert-danger");
            writeSection(context, component, notices.getWarnings(), "alert alert-thin alert-warning");
            writer.endElement("div"); // End Sections Div

            writer.endElement("div"); // End Content Div
        }
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        final boolean writeClosingDiv = (boolean) component.getAttributes().getOrDefault(WRITE_CLOSING_KEY, false);
        if (writeClosingDiv) {
            ResponseWriter writer = context.getResponseWriter();
            writer.endElement("div");
        }
    }

    private void writeSection(FacesContext context, UIComponent component, List<String> messages, String styleClass) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        if (Objects.nonNull(messages) && !messages.isEmpty()) {
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
