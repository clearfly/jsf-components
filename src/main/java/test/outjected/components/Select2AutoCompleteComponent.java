package test.outjected.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIInput;

import test.outjected.renderers.Select2AutoCompleteRenderer;

@FacesComponent(value = "test.outjected.components.Select2AutoCompleteComponent", namespace = Famlies.NAMESPACE)
public class Select2AutoCompleteComponent extends UIInput {
    public Select2AutoCompleteComponent() {
        setRendererType(Select2AutoCompleteRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Famlies.INPUT_COMPONENT_FAMILY;
    }
}
