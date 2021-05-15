package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;

import java.awt.*;

public class SwingCustomPeer implements SwingPeer{
    private Component component;

    public SwingCustomPeer(Component component) {
        this.component = component;
    }

    @Override
    public void install(AppComponent comp) {

    }

    @Override
    public Object toolkitComponent() {
        return component;
    }
}
