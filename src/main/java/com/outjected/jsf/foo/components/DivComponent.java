package com.outjected.jsf.foo.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.foo.renderers.DivRenderer;

@FacesComponent(value = "com.outjected.jsf.foo.components.DivComponent", namespace = Famlies.NAMESPACE)
public class DivComponent extends UIComponentBase {

    public DivComponent() {
        setRendererType(DivRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Famlies.OUTPUT_COMPONENT_FAMILY;
    }
}
