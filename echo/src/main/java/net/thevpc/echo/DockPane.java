package net.thevpc.echo;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppDock;
import net.thevpc.echo.impl.components.ContainerBase;
import net.thevpc.echo.spi.peers.AppDockPeer;

public class DockPane  extends ContainerBase<AppComponent> implements AppDock {
    public DockPane(String id,Application app) {
        super(id, app,AppDock.class, AppDockPeer.class,AppComponent.class);
    }
    public DockPane(Application app) {
        this(null,app);
    }

    public AppDockPeer peer() {
        return (AppDockPeer) super.peer();
    }

}
