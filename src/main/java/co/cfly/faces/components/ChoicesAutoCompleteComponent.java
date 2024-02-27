package co.cfly.faces.components;

import java.util.Collection;
import java.util.Collections;

import co.cfly.faces.renderers.ChoicesAutoCompleteRenderer;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.component.behavior.ClientBehaviorHolder;

@FacesComponent(value = "co.cfly.faces.components.ChoicesAutoCompleteComponent", namespace = Families.NAMESPACE)
public class ChoicesAutoCompleteComponent extends UIInput implements ClientBehaviorHolder {

    private static final Collection<String> EVENT_NAMES = Collections.singletonList("valueChange");

    public ChoicesAutoCompleteComponent() {
        setRendererType(ChoicesAutoCompleteRenderer.RENDERER_TYPE);
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
