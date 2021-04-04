package net.thevpc.echo;


import javax.swing.*;
import net.thevpc.common.props.WritableValue;

public interface AppWindow extends AppToolContainer {

    Application application();

    WritableValue<AppWindowDisplayMode> displayMode();

    WritableValue<AppMenuBar> menuBar();

    WritableValue<AppStatusBar> statusBar();

    WritableValue<AppToolBar> toolBar();

    WritableValue<AppWorkspace> workspace();

    WritableValue<String> title();

    WritableValue<ImageIcon> icon();

    AppWindowStateSetValue state();
    
    void centerOnDefaultMonitor();
    
    Object component();
}
