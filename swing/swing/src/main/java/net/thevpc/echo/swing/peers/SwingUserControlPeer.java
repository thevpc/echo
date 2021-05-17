package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.peers.AppUserControlPeer;

import java.awt.*;

public class SwingUserControlPeer implements SwingPeer, AppUserControlPeer {
    private Component component;

    public SwingUserControlPeer(Component component) {
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
