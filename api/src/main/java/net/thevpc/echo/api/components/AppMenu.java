package net.thevpc.echo.api.components;

import net.thevpc.echo.api.tools.AppComponentModel;

public interface AppMenu extends AppContainer<AppComponentModel, AppComponent> {
    boolean isActionable();
    void show(Object source,int x,int y);
}
