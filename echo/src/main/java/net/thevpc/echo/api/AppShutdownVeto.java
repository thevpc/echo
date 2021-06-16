package net.thevpc.echo.api;


public interface AppShutdownVeto {

    void vetoableChange(AppEvent event);
}
