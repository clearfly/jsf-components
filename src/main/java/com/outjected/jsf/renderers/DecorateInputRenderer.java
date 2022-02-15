package com.outjected.jsf.renderers;

import com.outjected.jsf.components.Families;
import com.outjected.jsf.utils.RendererTools;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessages;
import javax.faces.component.UISelectBoolean;
import javax.faces.component.html.HtmlSelectManyListbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;
import java.util.Iterator;

@SuppressWarnings("resource") @FacesRenderer(componentFamily = Families.OUTPUT_COMPONENT_FAMILY, rendererType = DecorateInputRenderer.RENDERER_TYPE) public class DecorateInputRenderer
        extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.DecorateInputRenderer";
    private static final String STYLE_CLASS_ATTR_NAME = "styleClass";
    private static final String SKIP_CONTROL_CLASS_ATTR_NAME = "skipControlClass";
    private static final String FORM_INPUT_STYLE = "form-control";
    private static final String FORM_SELECT_STYLE = "form-select";
    private static final String HAS_ERROR_STYLE = "has-error";

    @Override public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        final String label = (String) component.getAttributes().get("label");
        final UIComponent valueComponent = findValueComponent(component, label);

        // Write Outer Div
        final String style = (String) component.getAttributes().get("style");
        final String styleClass = (String) component.getAttributes().get("styleClass");
        final String errorsClass = hasErrors(context, valueComponent) ? HAS_ERROR_STYLE : null;
        final String divComputedStyleClass = RendererTools.spaceSeperateStrings("o-decorate-input form-group", styleClass, errorsClass);

        final String labelClass = (String) component.getAttributes().get("labelClass");
        final String help = (String) component.getAttributes().get("help");
        final String valueClass = (String) component.getAttributes().get("valueClass");
        writer.startElement("div", component); // Outer Div
        writeId(context, component);
        writeAttribute("class", divComputedStyleClass, context);
        writeAttribute("style", style, context);

        final boolean required = isRequired(valueComponent);
        final String valueComponentId = valueComponent.getClientId();

        // Write Label
        final String labelComputedStyleClass = RendererTools.spaceSeperateStrings("col-form-label", labelClass);
        writer.startElement("label", component); // Label
        writeAttribute("for", valueComponentId, context);
        writeAttribute("class", labelComputedStyleClass, context);
        writer.startElement("span", component);
        if (help != null) {
            writeAttribute("class", "popover-source", context);
            writeAttribute("data-bs-toggle", "popover", context);
            writeAttributeIfExists("helpContainer", "data-bs-container", context, component);
            writeAttributeIfExists("help", "data-bs-content", context, component);
            writeAttributeIfExistsOrDefault("helpContainer", "data-bs-container", "body", context, component);
            writeAttributeIfExistsOrDefault("helpPlacement", "data-bs-placement", "right", context, component);
            writeAttributeIfExistsOrDefault("helpTrigger", "data-bs-trigger", "hover", context, component);
            writeAttributeIfExistsOrDefault("helpDelay", "data-bs-delay", "0", context, component);
            writeAttributeIfExistsOrDefault("helpHtml", "data-bs-html", "true", context, component);
        }
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
        writer.startElement("div", component);
        writeAttribute("class", "invalid-feedback", context);
        UIMessages messages = new UIMessages();
        messages.setParent(valueComponent.getParent());
        messages.setFor(valueComponentId);
        messages.encodeAll(context);
        writer.endElement("div");

        writer.endElement("div"); // Value Div

    }

    @Override public void encodeChildren(FacesContext context, UIComponent component) {
        // Children are rendered manually in the encodeBegin so we don't want to render them twice
    }

    @Override public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
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

    private void encodeValue(FacesContext context, UIComponent component) throws IOException {
        for (UIComponent c : component.getChildren()) {
            final Boolean skipControlClass = (Boolean.valueOf((String) component.getAttributes().getOrDefault(SKIP_CONTROL_CLASS_ATTR_NAME, "false")));
            if (!skipControlClass && c instanceof EditableValueHolder) {
                String styleClass = (String) c.getAttributes().get(STYLE_CLASS_ATTR_NAME);
                if (c instanceof HtmlSelectOneMenu || c instanceof HtmlSelectManyListbox) {
                    styleClass = addStyleClassIfNecessary(styleClass, FORM_SELECT_STYLE);
                }
                else {
                    styleClass = addStyleClassIfNecessary(styleClass, FORM_INPUT_STYLE);
                }
                c.getAttributes().put(STYLE_CLASS_ATTR_NAME, styleClass);
            }
            c.encodeAll(context);
        }
    }

    private String addStyleClassIfNecessary(final String existing, final String targetClass) {
        if (existing == null || existing.isEmpty()) {
            return targetClass;
        }
        else if (!existing.contains(targetClass)) {
            return existing + " " + targetClass;
        }
        else {
            //Already exists so no need to add target
            return existing;
        }
    }
}
