package net.thevpc.echo.api.components;


import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.api.tools.AppToolWindow;

public interface AppWindow extends AppContainer<AppComponent, AppTool> {

    void centerOnDesktop();

    void close();

    AppToolWindow tool();
}
