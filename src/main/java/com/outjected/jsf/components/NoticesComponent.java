package com.outjected.jsf.components;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.outjected.jsf.core.Notices;

@FacesComponent(value = "com.outjected.jsf.components.NoticesComponent", namespace = Families.NAMESPACE)
public class NoticesComponent extends ComponentBase {

    @Override
    public String getFamily() {
        return Families.OUTPUT_COMPONENT_FAMILY;
    }

    @Override
    public boolean isRendered() {
        return super.isRendered() && getAttributes().get("notices") instanceof Notices notices && notices.isContainsNotices();
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        final Notices notices = (Notices) getAttributes().get("notices");
        final String errorTitle = (String) getAttributes().getOrDefault("errorTitle", "Has Errors -- Click for Details");
        final String warningTitle = (String) getAttributes().getOrDefault("warningTitle", "Has Warnings -- Click for Details");
        final boolean expanded = getAttribute("expanded", true);

        if (notices.isContainsNotices()) {
            final ResponseWriter writer = context.getResponseWriter();
            writer.startElement("div", this); //Start Outer div
            writeId(context);

            writer.startElement("div", this); // Start Content div
            writer.writeAttribute("onclick", "$('#noticesContentDiv').slideToggle('slow'); $('#noticesTitleDiv').slideToggle('slow');", null);

            // Title Div
            writer.startElement("div", this); //Start title div
            writer.writeAttribute("id", "noticesTitleDiv", null);
            writer.writeAttribute("class", notices.containsErrors() ? "alert alert-danger alert-thin text-center" : "alert alert-warning alert-thin text-center", null);
            writer.writeAttribute("style", "display:" + (expanded ? "none" : "block") + ";", null);
            writer.startElement("strong", this);
            writer.write(notices.containsErrors() ? errorTitle : warningTitle);
            writer.endElement("strong");
            writer.endElement("div"); // End Title Div

            writer.startElement("div", this);  // Start Sections div
            writer.writeAttribute("id", "noticesContentDiv", null);
            writer.writeAttribute("style", "display:" + (expanded ? "block" : "none") + ";", null);
            writeSection(context, notices.getErrors(), "alert alert-thin alert-danger");
            writeSection(context, notices.getWarnings(), "alert alert-thin alert-warning");
            writer.endElement("div"); // End Sections Div

            writer.endElement("div"); // End Content Div
        }
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        writer.endElement("div");
    }

    private void writeSection(FacesContext context, List<String> messages, String styleClass) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        if (Objects.nonNull(messages) && !messages.isEmpty()) {
            writer.startElement("div", this);
            writer.writeAttribute("class", styleClass, null);
            writer.startElement("ul", this);

            for (String message : messages) {
                writer.startElement("li", this);
                writer.write(message);
                writer.endElement("li");
            }

            writer.endElement("ul");
            writer.endElement("div");
        }
    }
}
