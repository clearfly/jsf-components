package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.CopyRenderer;

@FacesComponent(value = "com.outjected.jsf.components.CopyComponent", namespace = Families.NAMESPACE)
public class CopyComponent extends UIComponentBase {

    public CopyComponent() {
        setRendererType(CopyRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Families.OUTPUT_COMPONENT_FAMILY;
    }
}
