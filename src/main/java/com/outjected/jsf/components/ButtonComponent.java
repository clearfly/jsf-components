package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.ButtonRenderer;

@FacesComponent(value = "com.outjected.jsf.components.ButtonComponent", namespace = Famlies.NAMESPACE)
public class ButtonComponent extends UIComponentBase {

    public ButtonComponent() {
        setRendererType(ButtonRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Famlies.OUTPUT_COMPONENT_FAMILY;
    }
}
