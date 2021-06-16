package net.thevpc.echo;

import net.thevpc.echo.api.components.AppCalendar;
import net.thevpc.echo.impl.components.ControlBase;
import net.thevpc.echo.spi.peers.AppCalendarPeer;

public class Calendar extends ControlBase implements AppCalendar {
    public Calendar(String id, Application app) {
        super(id,app, AppCalendar.class, AppCalendarPeer.class
        );
    }

    public Calendar(Application app) {
        this(null,app);
    }

}

