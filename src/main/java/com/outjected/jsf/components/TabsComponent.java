package com.outjected.jsf.components;

import com.outjected.jsf.renderers.PanelRenderer;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;

@FacesComponent(value = "com.outjected.jsf.components.TabsComponent", namespace = Famlies.NAMESPACE) public class TabsComponent extends UIComponentBase {

    public TabsComponent() {
        setRendererType(PanelRenderer.RENDERER_TYPE);
    }

    @Override public String getFamily() {
        return Famlies.OUTPUT_COMPONENT_FAMILY;
    }

    @Override public boolean getRendersChildren() {
        return true;
    }

    @Override public boolean isRendered() {
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
