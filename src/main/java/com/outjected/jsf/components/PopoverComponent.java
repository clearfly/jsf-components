package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.PopoverRenderer;

@FacesComponent(value = "com.outjected.jsf.components.PopoverComponent", namespace = Families.NAMESPACE)
public class PopoverComponent extends UIComponentBase {

    public PopoverComponent() {
        setRendererType(PopoverRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Families.OUTPUT_COMPONENT_FAMILY;
    }
}
