package net.thevpc.echo;

import net.thevpc.echo.api.components.AppComboBox;
import net.thevpc.echo.api.components.AppToggleControl;

public class CheckBoxGroup<T> extends ToggleControlGroup<T>  {
    public CheckBoxGroup(Class<T> itemType, Application app) {
        this(null, itemType, app);
    }

    public CheckBoxGroup(String id, Class<T> itemType, Application app) {
        super(id, itemType, app);
        selection().multipleSelection().set(true);
        selection().noSelection().set(true);
    }

    @Override
    protected AppToggleControl createToggleControl() {
        return new CheckBox(null, null, null, app());
    }
}

