package net.thevpc.echo.impl.tools;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppToolAlert;
import net.thevpc.echo.api.tools.AppToolCalendar;

public class ToolCalendar extends AppToolBase implements AppToolCalendar {

    public ToolCalendar(String id, Application app) {
        super(id, app);
    }

    public ToolCalendar(Application app) {
        super(null, app);
    }


}
