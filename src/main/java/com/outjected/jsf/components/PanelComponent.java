package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.PanelRenderer;

@FacesComponent(value = "com.outjected.jsf.components.PanelComponent", namespace = Famlies.NAMESPACE)
public class PanelComponent extends UIComponentBase {

    public PanelComponent() {
        setRendererType(PanelRenderer.RENDERER_TYPE);
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
