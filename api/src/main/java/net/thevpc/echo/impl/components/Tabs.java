package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppTabs;
import net.thevpc.echo.api.peers.AppTabsPeer;
import net.thevpc.echo.api.tools.AppContainerModel;
import net.thevpc.echo.impl.tools.ContainerModel;

public class Tabs extends WindowContainerBase implements AppTabs {
    public Tabs(AppContainerModel folder) {
        super(folder, AppContainerModel.class, AppTabs.class, AppTabsPeer.class);
    }
    public Tabs(Application app) {
        this(new ContainerModel(app));
    }
}

