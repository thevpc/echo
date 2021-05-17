package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Path;
import net.thevpc.echo.Application;
import net.thevpc.echo.DefaultAppComponentOptions;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.api.peers.AppToolBarPeer;
import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.api.tools.AppContainerModel;
import net.thevpc.echo.impl.tools.ContainerModel;

public class ToolBar extends AppContainerBase<AppComponentModel, AppComponent> implements AppToolBar {
    public ToolBar(AppContainerModel tool) {
        super(tool,
                AppContainerModel.class, AppToolBar.class, AppToolBarPeer.class,
                AppComponentModel.class, AppComponent.class);
    }
    public ToolBar(Application app) {
        this(new ContainerModel(app));
    }

    @Override
    public AppComponent createPreferredChild(String name, Path absolutePath) {
        return new Menu(app());
    }

}

