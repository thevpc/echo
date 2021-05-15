package net.thevpc.echo.impl.tools;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppToolFolder;

public class ToolFolder extends AppToolBase implements AppToolFolder {

    public ToolFolder(String id, Application app) {
        super(id,app);
    }
    public ToolFolder( Application app) {
        super(null,app);
    }
}
