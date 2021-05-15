package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppChoice;
import net.thevpc.echo.api.components.AppComboBox;
import net.thevpc.echo.api.tools.AppToolChoice;
import net.thevpc.echo.impl.tools.ToolChoice;

public class ComboBox<T> extends Choice<T> implements AppComboBox<T> {
    public ComboBox(AppToolChoice<T> tool) {
        super(tool);
    }
    public ComboBox(Class<T> componentType, Application app) {
        super(componentType,app);
    }

    public AppToolChoice<T> tool() {
        return (AppToolChoice<T>) super.tool();
    }

}

