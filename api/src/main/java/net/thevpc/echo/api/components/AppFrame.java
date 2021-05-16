package net.thevpc.echo.api.components;


import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.*;
import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.api.tools.AppToolFrame;

public interface AppFrame extends AppContainer<AppComponent, AppTool> {


    WritableValue<AppMenuBar> menuBar();

    WritableValue<AppToolBarGroup> statusBar();

    WritableValue<AppToolBarGroup> toolBar();

    WritableValue<AppWorkspace> workspace();

    AppToolFrame tool();

    void centerOnDefaultMonitor();
    
    void close();
}
