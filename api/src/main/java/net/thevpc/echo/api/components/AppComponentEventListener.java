package net.thevpc.echo.api.components;

import net.thevpc.echo.api.AppEvent;

public interface AppComponentEventListener {
    void onEvent(AppComponentEvent event);
}
