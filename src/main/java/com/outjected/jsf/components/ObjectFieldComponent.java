package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.ObjectFieldRenderer;
import com.outjected.jsf.utils.RendererTools;

@FacesComponent(value = "com.outjected.jsf.components.ObjectFieldComponent", namespace = Families.NAMESPACE)
public class ObjectFieldComponent extends UIComponentBase {

    public ObjectFieldComponent() {
        setRendererType(ObjectFieldRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Families.OUTPUT_COMPONENT_FAMILY;
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    @Override
    public boolean isRendered() {
        if (!super.isRendered()) {
            //If explicitly set to not render then don't render
            return false;
        }
        else {
            final boolean hasChildrenToRender = getChildren().stream().anyMatch(UIComponent::isRendered);
            final boolean renderEmpty = RendererTools.attributeValueAsBoolean(getAttributes().get("renderEmpty"), false);
            //Only Render if some of the children are set to render
            return hasChildrenToRender || renderEmpty;
        }
    }
}
