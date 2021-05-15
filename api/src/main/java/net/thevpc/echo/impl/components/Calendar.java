package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppCalendar;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.tools.AppToolCalendar;
import net.thevpc.echo.impl.tools.ToolCalendar;

public class Calendar extends AppComponentBase implements AppCalendar {
    public Calendar(AppToolCalendar tool) {
        super(tool);
    }
    public Calendar(Application app) {
        super(new ToolCalendar(app));
    }


    public AppToolCalendar tool() {
        return (AppToolCalendar) super.tool();
    }

}

