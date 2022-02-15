package com.outjected.jsf.components;

import java.io.IOException;
import java.util.Objects;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.outjected.jsf.utils.RendererTools;
import com.sun.faces.facelets.compiler.UIInstructions;

@FacesComponent(value = "com.outjected.jsf.components.CardComponent", namespace = Families.NAMESPACE)
public class CardComponent extends ComponentBase {

    @Override
    public String getFamily() {
        return Families.OUTPUT_COMPONENT_FAMILY;
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    @Override
    public boolean isRendered() {
        if (!super.isRendered()) {
            //If explicitly set to not render then don't render
            return false;
        }
        else {
            //Only Render if some of the children are set to render
            return getChildren().stream().anyMatch(UIComponent::isRendered);
        }
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        final UIComponent headerFacet = getFacet("header");

        final String header = (String) getAttributes().get("header");
        final String title = (String) getAttributes().get("title");
        final String subtitle = (String) getAttributes().get("subtitle");

        // Write Outer Div
        final String style = (String) getAttributes().get("style");
        final String styleClass = (String) getAttributes().get("styleClass");
        final String divComputedStyleClass = RendererTools.spaceSeperateStrings("card", styleClass);
        writer.startElement("div", this); // Outer Div
        writeId(context);
        writeAttribute("class", divComputedStyleClass, context);
        writeAttribute("style", style, context);

        if (Objects.nonNull(header) && Objects.nonNull(headerFacet)) {
            throw new IllegalArgumentException("Cannot define both a top facet and a header");
        }

        if (Objects.nonNull(headerFacet) && headerFacet.isRendered()) {
            if (headerFacet instanceof UIInstructions || headerFacet.getChildren().stream().anyMatch(UIComponent::isRendered)) {
                writer.startElement("div", this);
                writer.writeAttribute("class", "card-header", null);
                headerFacet.encodeAll(context);
                writer.endElement("div");
            }
        }
        else if (Objects.nonNull(header)) {
            writer.startElement("h5", this);
            writer.writeAttribute("class", "card-header", null);
            writer.write(header);
            writer.endElement("h5");
        }

        writer.startElement("div", this); //
        writer.writeAttribute("class", "card-body", null);

        if (Objects.nonNull(title)) {
            writer.startElement("h6", this);
            writer.writeAttribute("class", "card-title", null);
            writer.write(title);
            writer.endElement("h6");
        }
        if (Objects.nonNull(subtitle)) {
            writer.startElement("h6", this);
            writer.writeAttribute("class", "card-subtitle mb-2 text-muted", null);
            writer.write(subtitle);
            writer.endElement("h6");
        }
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        writer.endElement("div"); //Body Div

        final UIComponent footerFacet = getFacet("footer");
        if (Objects.nonNull(footerFacet) && footerFacet.isRendered()) {
            if (footerFacet instanceof UIInstructions || footerFacet.getChildren().stream().anyMatch(UIComponent::isRendered)) {
                writer.startElement("div", this);
                writer.writeAttribute("class", "card-footer", null);
                footerFacet.encodeAll(context);
                writer.endElement("div");
            }
        }
        writer.endElement("div"); // Outer Div
    }
}
