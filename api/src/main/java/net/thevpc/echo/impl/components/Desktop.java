package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppDesktop;
import net.thevpc.echo.api.tools.AppToolFolder;
import net.thevpc.echo.impl.tools.ToolFolder;

public class Desktop extends WindowContainerBase implements AppDesktop {
    public Desktop(AppToolFolder folder) {
        super(folder);
    }

    public Desktop(Application app) {
        super(new ToolFolder(app));
    }

}

