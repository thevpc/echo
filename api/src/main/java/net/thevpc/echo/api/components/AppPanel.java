package net.thevpc.echo.api.components;

import net.thevpc.echo.api.components.AppControl;
import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.api.tools.AppToolFolder;

public interface AppPanel extends AppContainer<AppComponent, AppTool> {
    AppToolFolder tool();

}
