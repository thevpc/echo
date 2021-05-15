package net.thevpc.echo.impl.components;

import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.echo.api.components.AppComponent;

public class NameBoundPropertyListener<C extends AppComponent> implements PropertyListener {
    private String name;
    private AppContainerChildren<C, ?> children;

    public NameBoundPropertyListener(String name, AppContainerChildren<C, ?> children) {
        this.name = name;
        this.children = children;
    }

    @Override
    public void propertyUpdated(PropertyEvent event) {
        AppComponent o = event.getOldValue();
        if (o != null) {
            children.remove((C) o);
        }
        AppComponent m = event.getNewValue();
        if (m != null) {
            children.add((C) m, name);
        }
    }
}
