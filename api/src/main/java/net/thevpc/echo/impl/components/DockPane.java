package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.api.tools.AppToolFolder;
import net.thevpc.echo.impl.tools.ToolFolder;

public class DockPane  extends WindowContainerBase implements AppDock {
    public DockPane(AppToolFolder folder) {
        super(folder);
    }
    public DockPane(Application app) {
        super(new ToolFolder(app));
    }

}
