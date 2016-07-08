package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.DivRenderer;

@FacesComponent(value = "com.outjected.jsf.components.DivComponent", namespace = Famlies.NAMESPACE)
public class DivComponent extends UIComponentBase {

    public DivComponent() {
        setRendererType(DivRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Famlies.OUTPUT_COMPONENT_FAMILY;
    }
}
