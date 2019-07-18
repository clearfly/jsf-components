package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.CardRenderer;

@FacesComponent(value = "com.outjected.jsf.components.TabComponent", namespace = Famlies.NAMESPACE)
public class TabComponent extends UIComponentBase {

    public TabComponent() {
        setRendererType(CardRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Famlies.OUTPUT_COMPONENT_FAMILY;
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }
}
