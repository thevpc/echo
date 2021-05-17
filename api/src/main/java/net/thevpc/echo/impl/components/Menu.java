package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Path;
import net.thevpc.echo.Application;
import net.thevpc.echo.DefaultAppComponentOptions;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppMenu;
import net.thevpc.echo.api.peers.AppMenuPeer;
import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.api.tools.AppContainerModel;
import net.thevpc.echo.impl.tools.ContainerModel;

public class Menu extends AppContainerBase<AppComponentModel, AppComponent> implements AppMenu {
    public Menu(AppContainerModel tool) {
        super(tool,
                AppContainerModel.class, AppMenu.class, AppMenuPeer.class,
                AppComponentModel.class, AppComponent.class);
    }
    public Menu(Application app) {
        this(new ContainerModel(app));
    }

    @Override
    public boolean isActionable() {
        return false;
    }

    @Override
    public void show(Object source, int x, int y) {

    }
    @Override
    public AppComponent createPreferredChild(String name, Path absolutePath) {
        return new Menu(app());
    }
}

