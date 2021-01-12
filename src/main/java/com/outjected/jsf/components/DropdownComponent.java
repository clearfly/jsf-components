package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.DropdownRenderer;
import com.outjected.jsf.utils.RendererTools;

@FacesComponent(value = "com.outjected.jsf.components.DropdownComponent", namespace = Famlies.NAMESPACE)
public class DropdownComponent extends UIComponentBase {

    public DropdownComponent() {
        setRendererType(DropdownRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Famlies.OUTPUT_COMPONENT_FAMILY;
    }

    @Override
    public boolean isRendered() {
        if (super.isRendered()) {
            final boolean hasChildrenToRender = getChildren().stream().anyMatch(UIComponent::isRendered);
            final boolean renderEmpty = RendererTools.attributeValueAsBoolean(getAttributes().get("renderEmpty"), false);
            return hasChildrenToRender || renderEmpty;
        }
        else {
            return false;
        }
    }
}
