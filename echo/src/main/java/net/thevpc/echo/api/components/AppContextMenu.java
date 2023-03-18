package net.thevpc.echo.api.components;

import net.thevpc.common.props.ObservableValue;

public interface AppContextMenu extends AppButtonContainer {
    ObservableValue<Boolean> actionable();
    void show(AppComponent source,int x,int y);
}
