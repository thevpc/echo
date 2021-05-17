package net.thevpc.echo.api.components;


import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.*;
import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.api.tools.AppFrameModel;

public interface AppFrame extends AppContainer<AppComponentModel, AppComponent> {


    WritableValue<AppMenuBar> menuBar();

    WritableValue<AppToolBarGroup> statusBar();

    WritableValue<AppToolBarGroup> toolBar();

    WritableValue<AppComponent> workspace();

    AppFrameModel model();

    void centerOnDefaultMonitor();
    
    void close();
}
