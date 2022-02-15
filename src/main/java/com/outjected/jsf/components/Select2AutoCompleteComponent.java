package com.outjected.jsf.components;

import java.util.Collection;
import java.util.Collections;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIInput;
import javax.faces.component.behavior.ClientBehaviorHolder;

import com.outjected.jsf.renderers.Select2AutoCompleteRenderer;

@FacesComponent(value = "com.outjected.jsf.components.Select2AutoCompleteComponent", namespace = Families.NAMESPACE)
public class Select2AutoCompleteComponent extends UIInput implements ClientBehaviorHolder {

    private static final Collection<String> EVENT_NAMES = Collections.singletonList("valueChange");

    public Select2AutoCompleteComponent() {
        setRendererType(Select2AutoCompleteRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Families.INPUT_COMPONENT_FAMILY;
    }

    @Override
    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }

    @Override
    public String getDefaultEventName() {
        return "valueChange";
    }
}
