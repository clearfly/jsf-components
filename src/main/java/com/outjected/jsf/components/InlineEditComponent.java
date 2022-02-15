package com.outjected.jsf.components;

import com.outjected.jsf.renderers.ButtonRenderer;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

@FacesComponent(value = "com.outjected.jsf.components.InlineEditComponent", namespace = Families.NAMESPACE)
public class InlineEditComponent extends UIComponentBase {

    public InlineEditComponent() {
        setRendererType(ButtonRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Families.OUTPUT_COMPONENT_FAMILY;
    }
}
