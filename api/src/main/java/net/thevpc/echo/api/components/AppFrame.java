package net.thevpc.echo.api.components;


import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.FrameDisplayMode;
import net.thevpc.echo.WindowStateSetValue;

public interface AppFrame extends AppContainer<AppComponent> {
    WritableBoolean closable();

    WritableBoolean iconifiable();

    void open();
    WindowStateSetValue state();

    WritableValue<FrameDisplayMode> displayMode();

    WritableValue<AppMenuBar> menuBar();

    WritableValue<AppToolBarGroup> statusBar();

    WritableValue<AppToolBarGroup> toolBar();

    WritableValue<AppComponent> content();

    void centerOnDefaultMonitor();
    
    void close();
}
