package test.outjected.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import test.outjected.renderers.ButtonRenderer;

@FacesComponent(value = "test.outjected.components.ButtonComponent", namespace = Famlies.NAMESPACE)
public class ButtonComponent extends UIComponentBase {

    public ButtonComponent() {
        setRendererType(ButtonRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Famlies.OUTPUT_COMPONENT_FAMILY;
    }
}
