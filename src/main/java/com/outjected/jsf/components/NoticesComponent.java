package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.NoticesRenderer;

@FacesComponent(value = "com.outjected.jsf.components.NoticesComponent", namespace = Famlies.NAMESPACE)
public class NoticesComponent extends UIComponentBase {

    public NoticesComponent() {
        setRendererType(NoticesRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Famlies.OUTPUT_COMPONENT_FAMILY;
    }
}
