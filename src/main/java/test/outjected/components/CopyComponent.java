package test.outjected.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import test.outjected.renderers.CopyRenderer;

@FacesComponent(value = "test.outjected.components.CopyComponent", namespace = Famlies.NAMESPACE)
public class CopyComponent extends UIComponentBase {

    public CopyComponent() {
        setRendererType(CopyRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return Famlies.OUTPUT_COMPONENT_FAMILY;
    }
}
