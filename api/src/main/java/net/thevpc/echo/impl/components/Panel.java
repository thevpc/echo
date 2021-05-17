package net.thevpc.echo.impl.components;

import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppPanel;
import net.thevpc.echo.api.peers.AppPanelPeer;
import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.api.tools.AppContainerModel;
import net.thevpc.echo.impl.tools.ContainerModel;

public class Panel extends AppContainerBase<AppComponentModel, AppComponent> implements AppPanel {
    public Panel(Application application) {
        this(new ContainerModel(application));
    }
    public Panel(AppContainerModel tool) {
        super(tool,
                AppContainerModel.class, AppPanel.class, AppPanelPeer.class,
                AppComponentModel.class, AppComponent.class);
    }
}
