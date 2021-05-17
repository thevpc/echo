package net.thevpc.echo.api.components;


import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.api.tools.AppWindowModel;

public interface AppWindow extends AppContainer<AppComponentModel, AppComponent> {

    void centerOnDesktop();

    void close();

    AppWindowModel model();

    void resize(double x,double y,double w,double h);
}
