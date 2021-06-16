package net.thevpc.echo.api.components;

import net.thevpc.common.props.ObservableValue;

public interface AppMenu extends AppButtonContainer {
    ObservableValue<Boolean> actionable();
    void show(Object source,int x,int y);
}
