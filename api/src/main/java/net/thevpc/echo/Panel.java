package net.thevpc.echo;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppPanel;
import net.thevpc.echo.constraints.Layout;
import net.thevpc.echo.impl.components.ContainerBase;
import net.thevpc.echo.spi.peers.AppPanelPeer;

public class Panel extends ContainerBase<AppComponent> implements AppPanel {
    public Panel(Application application) {
        this(null,null,application);
    }

    public Panel(Layout layout, Application application) {
        this(null,layout,application);
    }

    public Panel(String id, Layout layout,Application app) {
        super(id, app, AppPanel.class, AppPanelPeer.class, AppComponent.class);
        if (layout != null) {
            parentConstraints().add(layout);
        }
    }
}
