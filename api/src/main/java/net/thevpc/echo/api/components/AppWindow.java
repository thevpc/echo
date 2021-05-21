package net.thevpc.echo.api.components;


import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.WindowStateSetValue;

public interface AppWindow extends AppContainer<AppComponent> {

    WritableBoolean active();

    WritableBoolean closable();

    WritableBoolean iconifiable();

    WritableValue<AppComponent> component();

    WindowStateSetValue state();
    void close();

    void centerOnDesktop();

    void resize(double x,double y,double w,double h);
}
