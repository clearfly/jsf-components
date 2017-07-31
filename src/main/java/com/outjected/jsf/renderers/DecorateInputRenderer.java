package com.outjected.jsf.renderers;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIMessages;
import javax.faces.component.UISelectBoolean;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.components.Famlies;
import com.outjected.jsf.utils.RendererTools;

@SuppressWarnings("resource")
@FacesRenderer(componentFamily = Famlies.OUTPUT_COMPONENT_FAMILY, rendererType = DecorateInputRenderer.RENDERER_TYPE)
public class DecorateInputRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.DecorateInputRenderer";
    private static final String STYLE_CLASS_ATTR_NAME = "styleClass";
    private static final String SKIP_CONTROL_CLASS_ATTR_NAME = "skipControlClass";
    private static final String FORM_CONTROL_STYLE = "form-control";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        final String label = (String) component.getAttributes().get("label");
        final UIForm parentForm = RendererTools.parentForm(component);
        final boolean horizontalLayout = RendererTools.horzontalLayout(parentForm);
        final UIComponent valueComponent = findValueComponent(component, label);

        // Write Outer Div
        final String style = (String) component.getAttributes().get("style");
        final String styleClass = (String) component.getAttributes().get("styleClass");
        final String errorsClass = hasErrors(context, valueComponent) ? "has-error" : null;
        final String divComputedStyleClass = RendererTools.spaceSeperateStrings("o-decorate-input form-group", styleClass, errorsClass);

        final String labelClass = (String) component.getAttributes().getOrDefault("labelClass", horizontalLayout ? "col-md-4" : null);
        final String help = (String) component.getAttributes().get("help");
        final String valueClass = (String) component.getAttributes().getOrDefault("valueClass", horizontalLayout ? "col-md-6" : null);
        writer.startElement("div", component); // Outer Div
        writeId(context, component);
        writeAttribute("class", divComputedStyleClass, context);
        writeAttribute("style", style, context);

        final boolean required = isRequired(valueComponent);
        final String valueComponentId = valueComponent.getClientId();

        // Write Label
        final String labelHelpClass = help != null ? "popover-source" : null;
        final String labelComputedStyleClass = RendererTools.spaceSeperateStrings("control-label", labelClass, labelHelpClass);
        writer.startElement("label", component); // Label
        writeAttribute("for", valueComponentId, context);
        writeAttribute("title", label, context);
        writeAttribute("class", labelComputedStyleClass, context);
        if (help != null) {
            writeAttribute("data-toggle", "popover", context);
            writeAttributeIfExists("helpContainer", "data-container", context, component);
            writeAttributeIfExists("help", "data-content", context, component);
            writeAttributeIfExistsOrDefault("helpTitle", "data-title", label, context, component);
            writeAttributeIfExistsOrDefault("helpContainer", "data-container", "body", context, component);
            writeAttributeIfExistsOrDefault("helpPlacement", "data-placement", "right", context, component);
            writeAttributeIfExistsOrDefault("helpTrigger", "data-trigger", "click", context, component);
            writeAttributeIfExistsOrDefault("helpDelay", "data-delay", "0", context, component);
            writeAttributeIfExistsOrDefault("helpHtml", "data-html", "true", context, component);
        }
        writer.startElement("span", component);
        writer.write(label);
        if (required) {
            writer.startElement("span", component);
            writer.writeAttribute("class", "required", null);
            writer.write("*");
            writer.endElement("span");
        }
        writer.endElement("span");
        writer.endElement("label");

        // Write Value Div
        writer.startElement("div", component); // Value Div
        writeAttribute("class", valueClass, context);
        encodeValue(context, component);

        // Write Help Block and Messages
        writer.startElement("span", component);
        writeAttribute("class", "help-block", context);
        UIMessages messages = new UIMessages();
        messages.setParent(valueComponent.getParent());
        messages.setFor(valueComponentId);
        messages.encodeAll(context);
        writer.endElement("span");

        writer.endElement("div"); // Value Div

    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) {
        // Noop
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("div"); // Outer Div
    }

    private boolean hasErrors(FacesContext facesContext, UIComponent valueComponent) {
        EditableValueHolder valueHolder = (EditableValueHolder) valueComponent;
        if (!valueHolder.isValid()) {
            return true;
        }
        else {
            Iterator<FacesMessage> it = facesContext.getMessages(valueComponent.getClientId());
            while (it.hasNext()) {
                if (it.next().getSeverity().compareTo(FacesMessage.SEVERITY_WARN) >= 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private UIComponent findValueComponent(UIComponent component, String labelName) {
        int editableValueCount = 0;
        UIComponent editableValueHolder = null;
        for (UIComponent c : component.getChildren()) {
            if (c instanceof EditableValueHolder) {
                editableValueCount++;
                editableValueHolder = c;
            }
        }

        if (editableValueCount != 1) {
            throw new RuntimeException("Must have One and only One EditableValueHolder child. Has: " + editableValueCount + " Label: " + labelName);
        }

        return editableValueHolder;
    }

    private boolean isRequired(UIComponent valueComponent) {
        if (valueComponent instanceof UISelectBoolean) {
            // Don't show booleans as required. It is confusing
            return false;
        }
        else {
            return ((EditableValueHolder) valueComponent).isRequired();
        }
    }

    private void encodeValue(FacesContext facesContext, UIComponent component) throws IOException {
        for (UIComponent c : component.getChildren()) {
            Boolean skipControlClass = (Boolean.valueOf((String) component.getAttributes().getOrDefault(SKIP_CONTROL_CLASS_ATTR_NAME, "false")));
            if (!skipControlClass.booleanValue() && c instanceof EditableValueHolder) {
                String styleClass = (String) c.getAttributes().get(STYLE_CLASS_ATTR_NAME);
                if (styleClass != null) {
                    if (!styleClass.contains(FORM_CONTROL_STYLE)) {
                        styleClass = styleClass + " " + FORM_CONTROL_STYLE;
                        c.getAttributes().put(STYLE_CLASS_ATTR_NAME, styleClass);
                    }
                }
                else {
                    c.getAttributes().put(STYLE_CLASS_ATTR_NAME, FORM_CONTROL_STYLE);
                }
            }
            c.encodeAll(facesContext);
        }
    }
}
