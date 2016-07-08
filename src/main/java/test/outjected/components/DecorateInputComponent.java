package test.outjected.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import test.outjected.renderers.DecorateInputRenderer;

@FacesComponent(value = "test.outjected.components.DecorateInputComponent", namespace = Famlies.NAMESPACE)
public class DecorateInputComponent extends UIComponentBase {

    public DecorateInputComponent() {
        setRendererType(DecorateInputRenderer.RENDERER_TYPE);
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
