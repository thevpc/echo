package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.api.peers.AppDockPeer;
import net.thevpc.echo.api.tools.AppContainerModel;
import net.thevpc.echo.impl.tools.ContainerModel;

public class DockPane  extends WindowContainerBase implements AppDock {
    public DockPane(AppContainerModel folder) {
        super(folder, AppContainerModel.class,AppDock.class, AppDockPeer.class);
    }
    public DockPane(Application app) {
        this(new ContainerModel(app));
    }

    public AppDockPeer peer() {
        return (AppDockPeer) super.peer();
    }

}
