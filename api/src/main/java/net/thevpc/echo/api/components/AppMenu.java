package net.thevpc.echo.api.components;

import net.thevpc.echo.api.tools.AppTool;

public interface AppMenu extends AppContainer<AppComponent, AppTool> {
    boolean isActionable();
    void show(Object source,int x,int y);
}
