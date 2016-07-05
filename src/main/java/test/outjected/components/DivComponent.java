package test.outjected.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import test.outjected.renderers.DivRenderer;

@FacesComponent(value = "test.outjected.components.DivComponent", namespace = Famlies.NAMESPACE)
public class DivComponent extends UIComponentBase {

    public DivComponent() {
        setRendererType(DivRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Famlies.OUTPUT_COMPONENT_FAMILY;
    }
}
