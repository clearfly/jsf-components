package com.outjected.jsf.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.components.Famlies;
import com.sun.faces.renderkit.RenderKitUtils;

@SuppressWarnings("resource")
@FacesRenderer(componentFamily = Famlies.INPUT_COMPONENT_FAMILY, rendererType = Select2AutoCompleteRenderer.RENDERER_TYPE)
public class Select2AutoCompleteRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.Select2AutoCompleteRenderer";

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
        writeAttribute("class", "form-control", context);
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

        Boolean required = (Boolean) component.getAttributes().getOrDefault("required", Boolean.FALSE);
        Boolean allowClear = required.equals(Boolean.TRUE) ? Boolean.FALSE : Boolean.TRUE;
        String placeholder = (String) component.getAttributes().getOrDefault("placeholder", "Choose");
        String searchPath = (String) component.getAttributes().get("searchPath");
        String initPath = (String) component.getAttributes().get("initPath");
        String requestContextPath = context.getExternalContext().getRequestContextPath();

        if (searchPath == null) {
            throw new IOException("searchPath was not defined");
        }

        if (initPath != null && value != null && value.length() > 0) {
            String initScript = String.format("$.ajax('%s?id='+%s, { dataType: 'json'}).done(function(data) { $(document.getElementById('%s')).append(new Option(data.text, data.id, true, true)); });",
                    requestContextPath + initPath, value, divId);
            writer.write(initScript);
        }

        String baseScript = String.format("var s2 = $(document.getElementById('%s')).select2({theme: 'bootstrap-5', minimumInputLength: 2, allowClear: %s, placeholder: '%s',"
                        + "ajax: { url: '%s', quietMillis: 500, dataType: 'json', data: function (params) { return { q: params.term, page: params.page }; } },});", divId, allowClear, placeholder,
                requestContextPath + searchPath);

        writer.write(baseScript);
        writer.endElement("script");
    }
}
