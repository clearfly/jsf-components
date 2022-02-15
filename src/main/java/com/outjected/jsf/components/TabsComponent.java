package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.TabsRenderer;

@FacesComponent(value = "com.outjected.jsf.components.TabsComponent", namespace = Families.NAMESPACE)
public class TabsComponent extends UIComponentBase {

    public TabsComponent() {
        setRendererType(TabsRenderer.RENDERER_TYPE);
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
            //Only Render if some of the children are set to render
            return getChildren().stream().filter(c -> c instanceof TabComponent).anyMatch(UIComponent::isRendered);
        }
    }
}
