package com.outjected.jsf.renderers;

import java.io.IOException;
import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.components.Famlies;
import com.outjected.jsf.utils.RendererTools;

@SuppressWarnings("resource")
@FacesRenderer(componentFamily = Famlies.OUTPUT_COMPONENT_FAMILY, rendererType = DropdownRenderer.RENDERER_TYPE)
public class DropdownRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.DropdownRenderer";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        final boolean hasChildrenToRender = component.getChildren().stream().anyMatch(UIComponent::isRendered);
        final boolean renderEmpty = RendererTools.attributeValueAsBoolean(component.getAttributes().get("renderEmpty"), false);

        if (hasChildrenToRender || renderEmpty) {
            component.getAttributes().put(WRITE_CLOSING_KEY, true);
            final UIComponent toggleFacet = component.getFacet("toggle");
            final boolean alignRight = RendererTools.attributeValueAsBoolean(component.getAttributes().get("alignRight"), true);

            if (Objects.isNull(toggleFacet)) {
                throw new IllegalArgumentException("Toggle Facet must be defined");
            }

            final String styleClass = (String) component.getAttributes().get("styleClass");
            final String divComputedStyleClass = RendererTools.spaceSeperateStrings("dropdown", styleClass);

            writer.startElement("div", component);
            writer.writeAttribute("class", divComputedStyleClass, null);

            toggleFacet.encodeAll(context);

            writer.startElement("div", component);

            if (alignRight) {
                writer.writeAttribute("class", "dropdown-menu dropdown-menu-right", null);
            }
            else {
                writer.writeAttribute("class", "dropdown-menu", null);
            }

            writer.writeAttribute("aria-labelledby", "dropdownMenuButton", null);

            for (UIComponent child : component.getChildren()) {
                child.encodeAll(context);
            }

            writer.endElement("div"); // dropdown-menu div
        }
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        final boolean writeClosingDiv = (boolean) component.getAttributes().getOrDefault(WRITE_CLOSING_KEY, false);
        if (writeClosingDiv) {
            ResponseWriter writer = context.getResponseWriter();
            writer.endElement("div"); // dropdown div
        }
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) {
        // Children are rendered manually in the encodeBegin so we don't want to render them twice
    }
}
