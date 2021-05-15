package net.thevpc.echo.api.components;

import net.thevpc.echo.api.tools.AppTool;

public interface AppContextMenu extends AppContainer<AppComponent, AppTool> {
    boolean isActionable();
    void show(AppComponent source,int x,int y);
}
