package net.thevpc.echo.api;

import net.thevpc.echo.Application;

public interface AppEvent {

    Application app();
    Object getSource();
}
