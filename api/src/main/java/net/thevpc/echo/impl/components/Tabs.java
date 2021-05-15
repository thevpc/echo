package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppTabs;
import net.thevpc.echo.api.tools.AppToolFolder;
import net.thevpc.echo.impl.tools.ToolFolder;

public class Tabs extends WindowContainerBase implements AppTabs {
    public Tabs(AppToolFolder folder) {
        super(folder);
    }
    public Tabs(Application app) {
        super(new ToolFolder(app));
    }
}

