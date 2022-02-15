package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.DecorateOutputRenderer;

@FacesComponent(value = "com.outjected.jsf.components.DecorateOutputComponent", namespace = Families.NAMESPACE)
public class DecorateOutputComponent extends UIComponentBase {

    public DecorateOutputComponent() {
        setRendererType(DecorateOutputRenderer.RENDERER_TYPE);
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
