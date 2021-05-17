package net.thevpc.echo.api.components;

import net.thevpc.echo.api.tools.AppComponentModel;

public interface AppContextMenu extends AppContainer<AppComponentModel, AppComponent> {
    boolean isActionable();
    void show(AppComponent source,int x,int y);
}
