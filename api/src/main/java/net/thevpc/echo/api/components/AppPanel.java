package net.thevpc.echo.api.components;

import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.api.tools.AppContainerModel;

public interface AppPanel extends AppContainer<AppComponentModel, AppComponent> {
    AppContainerModel model();

}
