package net.thevpc.echo.impl.components;

import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.api.components.AppWindowContainer;
import net.thevpc.echo.api.tools.AppToolFolder;

public class WindowContainerBase extends AppContainerBase<AppWindow, AppToolFolder> implements AppWindowContainer {
    public WindowContainerBase(AppToolFolder folder) {
        super(folder,AppWindow.class,AppToolFolder.class);
    }

}

