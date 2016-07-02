package com.outjected.jsf.foo.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.foo.renderers.ButtonRenderer;

@FacesComponent(value = "com.outjected.jsf.foo.components.ButtonComponent", namespace = Famlies.NAMESPACE)
public class ButtonComponent extends UIComponentBase {

    public ButtonComponent() {
        setRendererType(ButtonRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Famlies.OUTPUT_COMPONENT_FAMILY;
    }
}
