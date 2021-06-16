package net.thevpc.echo.api.components;

import net.thevpc.common.props.PropertyListeners;

public interface AppComponentEvents extends PropertyListeners{

    void add(AppComponentEventListener listener, AppEventType handle, AppEventType... handles);

    void remove(AppComponentEventListener listener, AppEventType handle, AppEventType... handles);

    AppComponentEventListener[] getAll(AppEventType handle);
}
