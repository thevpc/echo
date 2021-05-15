package net.thevpc.echo.impl;

import net.thevpc.echo.impl.DefaultAppEvent;
import net.thevpc.echo.AppValueEvent;
import net.thevpc.echo.Application;

public class DefaultAppValueEvent<T> extends DefaultAppEvent implements AppValueEvent<T> {
    private T value;

    public DefaultAppValueEvent(Application application, Object source, T value) {
        super(application, source);
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
