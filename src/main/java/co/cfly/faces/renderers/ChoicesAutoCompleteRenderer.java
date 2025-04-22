package co.cfly.faces.renderers;

import java.io.IOException;
import java.util.Objects;

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
            final String requestContextPath = context.getExternalContext().getRequestContextPath();
            final ResponseWriter writer = context.getResponseWriter();

            writer.startElement("select", inputComponent);
            if (Objects.isNull(inputComponent.getAttributes().get("searchPath"))) {
                throw new IllegalStateException("searchPath was not defined");
            }
            if ((boolean) inputComponent.getAttributes().getOrDefault("disabled", false)) {
                writeAttribute("disabled", "true", context);
            }

            writeId(context, inputComponent);
            writeAttribute("name", inputComponent.getClientId(), context);
            writeAttribute("class", "form-select choices-autocomplete", context);
            writeAttribute("style", "width:100%", context);
            writeAttribute("data-init-value", getCurrentValue(context, component), context);
            writeAttribute("data-choices-autocomplete", "true", context);
            writeAttribute("data-init-path", requestContextPath + inputComponent.getAttributes().get("initPath"), context);
            writeAttribute("data-search-path", requestContextPath + inputComponent.getAttributes().get("searchPath"), context);

            RenderKitUtils.renderOnchange(context, inputComponent, false);
            writer.startElement("option", inputComponent);
            writer.writeAttribute("value", "", "value");
            writer.write((String) inputComponent.getAttributes().getOrDefault("placeholder", "Choose"));
            writer.endElement("option");
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
}
