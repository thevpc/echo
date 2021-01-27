package net.thevpc.echo.swing.core;

import net.thevpc.echo.AppEvent;
import net.thevpc.echo.Application;

public class DefaultAppEvent implements AppEvent {
    private Application application;
    private Object source;

    public DefaultAppEvent(Application application, Object source) {
        this.application = application;
        this.source = source;
    }

    @Override
    public Application getApplication() {
        return application;
    }

    @Override
    public Object getSource() {
        return source;
    }
}
