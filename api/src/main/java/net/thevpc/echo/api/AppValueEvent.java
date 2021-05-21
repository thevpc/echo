package net.thevpc.echo.api;


public interface AppValueEvent<T> extends AppEvent {
    T getValue();
}
