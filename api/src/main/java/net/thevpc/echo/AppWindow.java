package net.thevpc.echo;

import net.thevpc.common.props.WritablePValue;

import javax.swing.*;

public interface AppWindow extends AppToolContainer {

    Application application();

    WritablePValue<AppWindowDisplayMode> displayMode();

    WritablePValue<AppMenuBar> menuBar();

    WritablePValue<AppStatusBar> statusBar();

    WritablePValue<AppToolBar> toolBar();

    WritablePValue<AppWorkspace> workspace();

    WritablePValue<String> title();

    WritablePValue<ImageIcon> icon();

    AppWindowStateSetValue state();
    
    Object component();
}
