package co.cfly.faces.renderers;

import java.io.IOException;

import co.cfly.faces.components.Families;
import com.sun.faces.renderkit.RenderKitUtils;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.render.FacesRenderer;

@FacesRenderer(componentFamily = Families.INPUT_COMPONENT_FAMILY, rendererType = ChoicesAutoCompleteRenderer.RENDERER_TYPE)
public class ChoicesAutoCompleteRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "co.cfly.faces.renderers.ChoicesAutoCompleteRenderer";

    @Override
    public Object getConvertedValue(FacesContext context, UIComponent component, Object val) throws ConverterException {
        UIInput input = (UIInput) component;
        if (input.getConverter() != null) {
            return input.getConverter().getAsObject(context, component, (String) input.getSubmittedValue());
        }
        else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        UIInput inputComponent = (UIInput) component;
        final boolean disabled = (boolean) inputComponent.getAttributes().getOrDefault("disabled", false);
        String placeholder = (String) component.getAttributes().getOrDefault("placeholder", "Choose");

        final String value;
        if (inputComponent.getConverter() != null) {
            value = inputComponent.getConverter().getAsString(context, component, inputComponent.getValue());
        }
        else {
            value = inputComponent.getValue().toString();
        }

        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("select", component);
        writeId(context, component);
        String divId = component.getClientId();
        writeAttribute("value", value, context);
        writeAttribute("name", divId, context);
        writeAttribute("class", "form-select", context);
        writeAttribute("style", "width:100%", context);
        if (disabled) {
            writeAttribute("disabled", "true", context);
        }

        RenderKitUtils.renderOnchange(context, component, false);

        writer.startElement("option", inputComponent);
        writer.writeAttribute("value", "", "value");
        writer.write(placeholder);
        writer.endElement("option");

        writer.endElement("select");
        writeScript(context, writer, component, value, divId);
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) {
        // NOOP
    }

    private void writeScript(FacesContext context, ResponseWriter writer, UIComponent component, String value, String divId) throws IOException {
        writer.startElement("script", component);
        writer.writeAttribute("type", "text/javascript", null);

        String searchPath = (String) component.getAttributes().get("searchPath");
        String initPath = (String) component.getAttributes().get("initPath");
        String requestContextPath = context.getExternalContext().getRequestContextPath();

        if (searchPath == null) {
            throw new IOException("searchPath was not defined");
        }

        final String initOptions = (initPath != null && !initPath.isEmpty() && value != null && !value.isEmpty())
                ? String.format(", initPath: '%s', value: '%s'", requestContextPath + initPath, value)
                : "";
        final String options = String.format("{searchPath: '%s'%s}", requestContextPath + searchPath, initOptions);
        final String baseScript = String.format("upgradeChoicesAutoComplete(document.getElementById('%s'), %s);", divId, options);
        writer.write(baseScript);
        writer.endElement("script");
    }
}
