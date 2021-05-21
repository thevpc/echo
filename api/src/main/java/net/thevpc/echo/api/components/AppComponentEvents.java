package net.thevpc.echo.api.components;

public interface AppComponentEvents {
    void add(AppComponentEventListener listener,AppEventType handle,AppEventType ...handles);

    void remove(AppComponentEventListener listener,AppEventType handle,AppEventType ...handles);

    AppComponentEventListener[] getAll(AppEventType handle);
}
