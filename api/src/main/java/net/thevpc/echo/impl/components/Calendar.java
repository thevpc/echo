package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppCalendar;
import net.thevpc.echo.api.peers.AppCalendarPeer;
import net.thevpc.echo.api.tools.AppCalendarModel;
import net.thevpc.echo.impl.tools.CalendarModel;

public class Calendar extends AppControlBase implements AppCalendar {
    public Calendar(AppCalendarModel tool) {
        super(tool
                , AppCalendarModel.class, AppCalendar.class, AppCalendarPeer.class
        );
    }

    public Calendar(Application app) {
        this(new CalendarModel(app));
    }


    public AppCalendarModel model() {
        return (AppCalendarModel) super.model();
    }

}

