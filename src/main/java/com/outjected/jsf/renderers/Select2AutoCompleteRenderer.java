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

@FacesRenderer(componentFamily = Famlies.INPUT_COMPONENT_FAMILY, rendererType = Select2AutoCompleteRenderer.RENDERER_TYPE)
public class Select2AutoCompleteRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.Select2AutoCompleteRenderer";

    private String divId;

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

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        UIInput inputComponent = (UIInput) component;
        divId = component.getClientId();

        String value = null;
        if (inputComponent.getConverter() != null) {
            value = inputComponent.getConverter().getAsString(context, component, inputComponent.getValue());
        }
        else {
            value = inputComponent.getValue().toString();
        }

        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("input", component);
        writeId(context, component);
        writeAttribute("value", value, context);
        writeAttribute("type", "hidden", context);
        writeAttribute("name", divId, context);
        RenderKitUtils.renderOnchange(context, component, false);
        writer.endElement("input");
        writeScript(context, writer, component);
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        // NOOP
    }

    private void writeScript(FacesContext context, ResponseWriter writer, UIComponent component) throws IOException {
        writer.startElement("script", component);
        writer.writeAttribute("type", "text/javascript", null);

        boolean allowClear = !(boolean) component.getAttributes().getOrDefault("required", false);
        String placeholder = (String) component.getAttributes().getOrDefault("placeholder", "Choose");
        String searchPath = (String) component.getAttributes().get("searchPath");
        String initPath = (String) component.getAttributes().get("initPath");
        String requestContextPath = context.getExternalContext().getRequestContextPath();

        if (searchPath == null) {
            throw new IOException("searchPath was not defined");
        }

        if (initPath == null) {
            throw new IOException("initPath was not defined");
        }

        String content = String.format("var s2 = $(jsfId('%s')).select2({minimumInputLength: 2, allowClear: %s, placeholder: '%s',"
                + "ajax: { url: '%s', quietMillis: 500, dataType: 'json', data: function (term, page) { return { q: term }; }, results: function (data, page) { return { results: data }; } },"
                + "initSelection: function(element, callback) { var id=$(element).val(); if (id!=='') { $.ajax('%s?id='+id, { dataType: 'json'}).done(function(data) { callback(data); }); } } });",
                divId, allowClear, placeholder, requestContextPath + searchPath, requestContextPath + initPath);

        writer.write(content);
        writer.endElement("script");
    }
}
