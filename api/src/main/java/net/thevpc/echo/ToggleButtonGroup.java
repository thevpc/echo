package net.thevpc.echo;

import net.thevpc.echo.api.components.AppToggleControl;

public class ToggleButtonGroup<T> extends ToggleControlGroup<T>  {
    public ToggleButtonGroup(Class<T> itemType, Application app) {
        this(null, itemType, app);
    }

    public ToggleButtonGroup(String id, Class<T> itemType, Application app) {
        super(id, itemType, app);
        selection().multipleSelection().set(true);
        selection().noSelection().set(true);
    }

    @Override
    protected AppToggleControl createToggleControl() {
        return new ToggleButton(null, null, null, app());
    }
}

