package com.outjected.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.outjected.jsf.renderers.CardRenderer;

@FacesComponent(value = "com.outjected.jsf.components.CardComponent", namespace = Famlies.NAMESPACE)
public class CardComponent extends UIComponentBase {

    public CardComponent() {
        setRendererType(CardRenderer.RENDERER_TYPE);
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
