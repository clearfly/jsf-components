package com.outjected.jsf.foo.renderers;

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.foo.components.Famlies;
import com.outjected.jsf.foo.utils.RendererTools;

@ResourceDependencies({ @ResourceDependency(name = "ZeroClipboard.min.js", library = "com.outjected.jsf.foo") })
@FacesRenderer(componentFamily = Famlies.OUTPUT_COMPONENT_FAMILY, rendererType = CopyRenderer.RENDERER_TYPE)
public class CopyRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.CopyRenderer";

    private boolean closeNeeded;
    private String divId = null;

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        String value = (String) component.getAttributes().get("value");
        if (value != null) {
            divId = component.getClientId();
            closeNeeded = true;
            ResponseWriter writer = context.getResponseWriter();
            writer.startElement("div", component);
            writeId(context, component);

            // Write styleClass
            String copyClass = (String) component.getAttributes().get("copyClass");
            String styleClass = (String) component.getAttributes().get("styleClass");
            String styleClassValue = RendererTools.spaceSeperateStrings("clipboard-copy-div", copyClass, styleClass);
            writeAttribute("class", styleClassValue, context);

        }
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        if (closeNeeded) {
            ResponseWriter writer = context.getResponseWriter();
            writer.endElement("div");
            writer.startElement("script", component);
            writer.writeAttribute("type", "text/javascript", null);

            String resourcePath = context.getApplication().getResourceHandler().createResource("ZeroClipboard.swf", "com.outjected.jsf.foo").getRequestPath();
            String copyClass = (String) component.getAttributes().getOrDefault("copyClass", "icon-copy");
            String copiedClass = (String) component.getAttributes().getOrDefault("copied", "icon-paste");

            String content = String.format("startZclip('%s','%s', '#{component.attributes['copyClass']}', '#{component.attributes['copiedClass']}'", resourcePath, divId, copyClass, copiedClass);

            writer.write(content);
            writer.endElement("script");
        }
    }

}
