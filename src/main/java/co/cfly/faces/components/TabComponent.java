package co.cfly.faces.components;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponentBase;

@FacesComponent(value = "co.cfly.faces.components.TabComponent", namespace = Families.NAMESPACE)
public class TabComponent extends UIComponentBase {

    @Override
    public String getFamily() {
        return Families.OUTPUT_COMPONENT_FAMILY;
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }
}
