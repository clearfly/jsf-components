package com.outjected.jsf.foo.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.foo.renderers.CopyRenderer;

@FacesComponent(value = "com.outjected.jsf.foo.components.CopyComponent", namespace = Famlies.NAMESPACE)
public class CopyComponent extends UIComponentBase {

    public CopyComponent() {
        setRendererType(CopyRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Famlies.OUTPUT_COMPONENT_FAMILY;
    }
}
