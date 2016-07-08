package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.ModalRenderer;

@FacesComponent(value = "com.outjected.jsf.components.ModalComponent", namespace = Famlies.NAMESPACE)
public class ModalComponent extends UIComponentBase {

    public ModalComponent() {
        setRendererType(ModalRenderer.RENDERER_TYPE);
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
