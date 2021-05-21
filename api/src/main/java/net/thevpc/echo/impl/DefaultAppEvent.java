package net.thevpc.echo.impl;

import net.thevpc.echo.api.AppEvent;
import net.thevpc.echo.Application;

public class DefaultAppEvent implements AppEvent {
    private Application application;
    private Object source;

    public DefaultAppEvent(Application application, Object source) {
        this.application = application;
        this.source = source;
    }

    @Override
    public Application app() {
        return application;
    }

    @Override
    public Object getSource() {
        return source;
    }
}
