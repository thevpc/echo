package net.thevpc.echo.impl.tools;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppToolTable;
import net.thevpc.echo.api.tools.AppToolTree;

public class ToolTree extends AppToolBase implements AppToolTree {
    public ToolTree(String id, Application app) {
        super(id, app);
    }

    public ToolTree(Application app) {
        super(app);
    }

}
