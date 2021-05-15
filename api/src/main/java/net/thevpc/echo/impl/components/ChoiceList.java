package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppChoiceList;
import net.thevpc.echo.api.components.AppComboBox;
import net.thevpc.echo.api.tools.AppToolChoice;

public class ChoiceList<T> extends Choice<T> implements AppChoiceList<T> {
    public ChoiceList(AppToolChoice<T> tool) {
        super(tool);
    }
    public ChoiceList(Class<T> componentType, Application app) {
        super(componentType,app);
    }

    public AppToolChoice<T> tool() {
        return (AppToolChoice<T>) super.tool();
    }

}

