package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.DropdownRenderer;

@FacesComponent(value = "com.outjected.jsf.components.DropdownComponent", namespace = Famlies.NAMESPACE)
public class DropdownComponent extends UIComponentBase {

    public DropdownComponent() {
        setRendererType(DropdownRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Famlies.OUTPUT_COMPONENT_FAMILY;
    }
}
