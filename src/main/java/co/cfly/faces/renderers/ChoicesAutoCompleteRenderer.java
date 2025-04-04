package co.cfly.faces.renderers;

import java.io.IOException;

import co.cfly.faces.components.Families;
import com.sun.faces.renderkit.RenderKitUtils;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;
import jakarta.faces.render.FacesRenderer;

@FacesRenderer(componentFamily = Families.INPUT_COMPONENT_FAMILY, rendererType = ChoicesAutoCompleteRenderer.RENDERER_TYPE)
public class ChoicesAutoCompleteRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "co.cfly.faces.renderers.ChoicesAutoCompleteRenderer";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (component instanceof UIInput inputComponent) {
            final String currentValue = getCurrentValue(context, component);
            final String requestContextPath = context.getExternalContext().getRequestContextPath();

            final boolean disabled = (boolean) inputComponent.getAttributes().getOrDefault("disabled", false);
            String placeholder = (String) inputComponent.getAttributes().getOrDefault("placeholder", "Choose");

            ResponseWriter writer = context.getResponseWriter();
            writer.startElement("select", inputComponent);
            writeId(context, inputComponent);
            String divId = inputComponent.getClientId();
            writeAttribute("value", currentValue, context);
            writeAttribute("name", divId, context);
            writeAttribute("class", "form-select choices-autocomplete", context);
            writeAttribute("style", "width:100%", context);
            writeAttribute("data-init-value", currentValue, context);
            writeAttribute("data-choices-autocomplete", "true", context);
            writeAttribute("data-init-path", requestContextPath + inputComponent.getAttributes().get("initPath"), context);
            writeAttribute("data-search-path", requestContextPath + inputComponent.getAttributes().get("searchPath"), context);
            if (disabled) {
                writeAttribute("disabled", "true", context);
            }

            RenderKitUtils.renderOnchange(context, inputComponent, false);

            writer.startElement("option", inputComponent);
            writer.writeAttribute("value", "", "value");
            writer.write(placeholder);
            writer.endElement("option");
            writeScript(context, writer, inputComponent);
            writer.endElement("select");
        }
        else {
            throw new RuntimeException("%s is not an instance of UIInput".formatted(component));
        }
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        // NOOP
    }

    private void writeScript(FacesContext context, ResponseWriter writer, UIComponent component) throws IOException {
        writer.startElement("script", component);
        writer.writeAttribute("type", "text/javascript", null);
        writer.write("upgradeChoicesAutocompletes();");
        writer.endElement("script");
    }
}
