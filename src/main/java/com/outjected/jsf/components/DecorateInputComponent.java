package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.DecorateInputRenderer;

@FacesComponent(value = "com.outjected.jsf.components.DecorateInputComponent", namespace = Families.NAMESPACE)
public class DecorateInputComponent extends UIComponentBase {

    public DecorateInputComponent() {
        setRendererType(DecorateInputRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Families.OUTPUT_COMPONENT_FAMILY;
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }
}
