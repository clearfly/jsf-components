package com.outjected.jsf.components;

import java.io.IOException;
import java.util.Objects;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.outjected.jsf.utils.RendererTools;

@FacesComponent(value = "com.outjected.jsf.components.DropdownComponent", namespace = Families.NAMESPACE)
public class DropdownComponent extends ComponentBase {

    @Override
    public String getFamily() {
        return Families.OUTPUT_COMPONENT_FAMILY;
    }

    @Override
    public boolean isRendered() {
        if (super.isRendered()) {
            final boolean hasChildrenToRender = getChildren().stream().anyMatch(UIComponent::isRendered);
            final boolean renderEmpty = getAttribute("renderEmpty", false);
            return hasChildrenToRender || renderEmpty;
        }
        else {
            return false;
        }
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        final UIComponent toggleFacet = getFacet("toggle");
        final boolean alignRight = getAttribute("alignRight", true);
        final boolean positionStatic = getAttribute("positionStatic", false);

        if (Objects.isNull(toggleFacet)) {
            throw new IllegalArgumentException("Toggle Facet must be defined");
        }

        final String styleClass = (String) getAttributes().get("styleClass");
        final String positionClass = positionStatic ? "position-static" : null;
        final String divComputedStyleClass = RendererTools.spaceSeperateStrings("dropdown", positionClass, styleClass);

        writer.startElement("div", this);
        writer.writeAttribute("class", divComputedStyleClass, null);

        toggleFacet.encodeAll(context);

        writer.startElement("div", this);

        if (alignRight) {
            writer.writeAttribute("class", "dropdown-menu dropdown-menu-right", null);
        }
        else {
            writer.writeAttribute("class", "dropdown-menu", null);
        }

        writer.writeAttribute("aria-labelledby", "dropdownMenuButton", null);
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("div"); // dropdown-menu div
        writer.endElement("div"); // dropdown div
    }
}
