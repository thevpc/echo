package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppUserControl;
import net.thevpc.echo.spi.peers.AppUserControlPeer;

import java.awt.*;

public class SwingUserControlPeer implements SwingPeer, AppUserControlPeer {
    private Component component;
    private AppComponent comp;

    public SwingUserControlPeer() {
    }

    @Override
    public void install(AppComponent comp) {
        this.comp=comp;
        Object a = ((AppUserControl) comp).renderer().get();
        this.component =(Component) a;
    }

    @Override
    public Object toolkitComponent() {
        return component;
    }
}
