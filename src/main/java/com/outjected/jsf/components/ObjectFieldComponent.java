package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.ObjectFieldRenderer;

@FacesComponent(value = "com.outjected.jsf.components.ObjectFieldComponent", namespace = Famlies.NAMESPACE)
public class ObjectFieldComponent extends UIComponentBase {

    public ObjectFieldComponent() {
        setRendererType(ObjectFieldRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Famlies.OUTPUT_COMPONENT_FAMILY;
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }
}
