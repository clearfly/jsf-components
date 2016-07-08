package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.CopyRenderer;

@FacesComponent(value = "com.outjected.jsf.components.CopyComponent", namespace = Famlies.NAMESPACE)
public class CopyComponent extends UIComponentBase {

    public CopyComponent() {
        setRendererType(CopyRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Famlies.OUTPUT_COMPONENT_FAMILY;
    }
}
