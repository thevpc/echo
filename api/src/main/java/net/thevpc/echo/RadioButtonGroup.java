package net.thevpc.echo;

import net.thevpc.common.props.PropertyType;
import net.thevpc.echo.api.components.AppToggleControl;

public class RadioButtonGroup<T> extends ToggleControlGroup<T> {

    public RadioButtonGroup(Class<T> itemType, Application app) {
        this(null, itemType, app);
    }

    public RadioButtonGroup(String id, Class<T> itemType, Application app) {
        this(id, PropertyType.of(itemType), app);
    }

    public RadioButtonGroup(String id, PropertyType itemType, Application app) {
        super(id, itemType, app);
        selection().multipleSelection().set(false);
        selection().noSelection().set(false);
    }

    @Override
    protected AppToggleControl createToggleControl() {
        return new RadioButton(null, null, null, app());
    }
}
