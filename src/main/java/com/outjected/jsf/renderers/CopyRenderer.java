package com.outjected.jsf.renderers;

import java.io.IOException;

import javax.faces.application.Resource;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.components.Famlies;
import com.outjected.jsf.utils.RendererTools;

@ResourceDependencies({ @ResourceDependency(name = "ZeroClipboard.min.js", library = "com.outjected.jsf.core") })
@FacesRenderer(componentFamily = Famlies.OUTPUT_COMPONENT_FAMILY, rendererType = CopyRenderer.RENDERER_TYPE)
public class CopyRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.CopyRenderer";

    private boolean closeNeeded;
    private String divId = null;

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        Object objectValue = component.getAttributes().get("value");
        if (objectValue != null) {
            String value = null;
            if (objectValue instanceof String) {
                value = (String) objectValue;
            }
            else {
                value = objectValue.toString();
            }
            divId = component.getClientId();
            closeNeeded = true;
            ResponseWriter writer = context.getResponseWriter();
            writer.startElement("div", component);
            writeId(context, component);

            // Write styleClass
            String copyClass = (String) component.getAttributes().getOrDefault("copyClass", "icon-copy");
            String styleClass = (String) component.getAttributes().get("styleClass");
            String styleClassValue = RendererTools.spaceSeperateStrings("clipboard-copy-div", copyClass, styleClass);
            writeAttribute("class", styleClassValue, context);
            writeAttribute("data-clipboard-text", value, context);

        }
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        if (closeNeeded) {
            ResponseWriter writer = context.getResponseWriter();
            writer.endElement("div");
            writer.startElement("script", component);
            writer.writeAttribute("type", "text/javascript", null);

            Resource resource = context.getApplication().getResourceHandler().createResource("ZeroClipboard.swf", "com.outjected.jsf.core");
            String resourcePath = resource != null ? resource.getRequestPath() : "Missing";
            String copyClass = (String) component.getAttributes().getOrDefault("copyClass", "icon-copy");
            String copiedClass = (String) component.getAttributes().getOrDefault("copied", "icon-paste");

            String content = String.format("startZclip('%s','%s', '%s', '%s');", divId, resourcePath, copyClass, copiedClass);

            writer.write(content);
            writer.endElement("script");
        }
    }

}
