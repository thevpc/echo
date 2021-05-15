package net.thevpc.echo.impl.tools;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.Str;
import net.thevpc.echo.api.components.AppAction;
import net.thevpc.echo.api.tools.AppActionValue;
import net.thevpc.echo.api.tools.AppToolAction;
import net.thevpc.echo.api.tools.AppToolAlert;
import net.thevpc.echo.props.AppWritableAction;

public class ToolAlert extends AppToolBase implements AppToolAlert {

    public ToolAlert(String id, Application app) {
        super(id, app);
    }

    public ToolAlert(Application app) {
        super(null, app);
    }


}
