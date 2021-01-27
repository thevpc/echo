package net.thevpc.echo;


public interface AppShutdownVeto {

    void vetoableChange(AppEvent event);
}
