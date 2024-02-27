package co.cfly.faces.components;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import co.cfly.faces.utils.RendererTools;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;

@FacesComponent(value = "co.cfly.faces.components.TabsComponent", namespace = Families.NAMESPACE)
public class TabsComponent extends ComponentBase {

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
        return super.isRendered() && getChildren().stream().filter(c -> c instanceof TabComponent).anyMatch(UIComponent::isRendered);
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        List<TabComponent> tabs = new ArrayList<>();

        for (UIComponent child : getChildren()) {
            if (child instanceof TabComponent && child.isRendered()) {
                tabs.add((TabComponent) child);
            }
        }

        if (tabs.isEmpty()) {
            // No tabs so don't render anything;
            return;
        }

        // Write Outer Div
        final String style = (String) getAttributes().get("style");
        final String styleClass = (String) getAttributes().get("styleClass");
        final String divComputedStyleClass = RendererTools.spaceSeperateStrings("tabs", styleClass);
        writer.startElement("div", this); // Outer Div
        writeId(context);
        writeAttribute("class", divComputedStyleClass, context);
        writeAttribute("style", style, context);

        writer.startElement("ul", this);
        writeAttribute("id", "tabs", context);
        writeAttribute("class", "nav nav-tabs", context);
        writeAttribute("role", "tablist", context);

        for (TabComponent tab : tabs) {
            // Write NAV Pills
            final String hash = (String) tab.getAttributes().get("hash");
            final String title = (String) tab.getAttributes().get("title");
            final Long count = RendererTools.asLong(tab.getAttributes().get("count"));
            final boolean hasCountBadge = Objects.nonNull(count) && count > 0;
            writer.startElement("li", this);
            writeAttribute("class", "nav-item", context);
            writeAttribute("role", "presentation", context);
            writer.startElement("button", this);
            writeAttribute("class", "nav-link", context);
            writeAttribute("data-bs-target", "#" + hash, context);
            writeAttribute("type", "button", context);

            writeAttribute("aria-controls", hash, context);
            writeAttribute("role", "tab", context);
            writeAttribute("data-bs-toggle", "tab", context);
            if (hasCountBadge) {
                writer.write(title + " ");
                writer.startElement("span", this);
                writeAttribute("class", "badge bg-primary", context);
                writer.write(count.toString());
                writer.endElement("span");
            }
            else {
                writer.write(title);
            }
            writer.endElement("button");
            writer.endElement("li");
        }
        writer.endElement("ul"); // End Pills

        writer.startElement("div", this); // Tab Content DIV

        writeAttribute("class", "tab-content", context);

        for (UIComponent child : getChildren()) {
            if (child instanceof TabComponent tab && child.isRendered()) {
                final String hash = (String) tab.getAttributes().get("hash");
                writer.startElement("div", this);
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
    public void encodeChildren(FacesContext context) {
        // Children are rendered manually in the encodeBegin so we don't want to render them twice
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        writer.endElement("div"); // Outer Div
    }
}
