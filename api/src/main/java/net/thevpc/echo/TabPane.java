package net.thevpc.echo;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppTabPane;
import net.thevpc.echo.impl.components.ContainerBase;
import net.thevpc.echo.spi.peers.AppTabPanePeer;

public class TabPane extends ContainerBase<AppComponent>
        implements AppTabPane {
    public TabPane(String id, Application app) {
        super(id, app,
                AppTabPane.class, AppTabPanePeer.class,
                AppComponent.class);
    }

    public TabPane(Application app) {
        this(null, app);
    }
}

