package net.thevpc.echo.impl.tools;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppToolAlert;

public class ToolAlert extends AppToolBase implements AppToolAlert {

    public ToolAlert(String id, Application app) {
        super(id, app);
    }

    public ToolAlert(Application app) {
        super(null, app);
    }


}
