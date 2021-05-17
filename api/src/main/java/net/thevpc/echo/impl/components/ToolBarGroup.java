package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Path;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.api.peers.AppToolBarGroupPeer;
import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.api.tools.AppContainerModel;
import net.thevpc.echo.impl.tools.ContainerModel;

public class ToolBarGroup extends AppContainerBase<AppComponentModel, AppComponent> implements AppToolBarGroup {
    public ToolBarGroup(AppContainerModel tool) {
        super(tool,
                AppContainerModel.class, AppToolBarGroup.class, AppToolBarGroupPeer.class,
                AppComponentModel.class, AppComponent.class
        );
    }
    public ToolBarGroup(Application app) {
        this(new ContainerModel(app));
    }
    @Override
    public AppComponent createPreferredChild(String name, Path absolutePath) {
        return new ToolBar(app());
    }

}

