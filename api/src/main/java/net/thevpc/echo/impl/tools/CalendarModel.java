package net.thevpc.echo.impl.tools;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppCalendarModel;

public class CalendarModel extends AppComponentModelBase implements AppCalendarModel {

    public CalendarModel(String id, Application app) {
        super(id, app);
    }

    public CalendarModel(Application app) {
        super(null, app);
    }


}
