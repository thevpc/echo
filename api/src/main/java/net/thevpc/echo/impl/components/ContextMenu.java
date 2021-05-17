package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Path;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppContextMenu;
import net.thevpc.echo.api.components.AppMenu;
import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.api.tools.AppContainerModel;
import net.thevpc.echo.api.peers.AppContextMenuPeer;
import net.thevpc.echo.impl.tools.ContainerModel;

public class ContextMenu extends AppContainerBase<AppComponentModel, AppComponent> implements AppContextMenu {
    public ContextMenu(AppContainerModel tool) {
        super(tool,
                AppContainerModel.class, AppContextMenu.class,AppContextMenuPeer.class,
                AppComponentModel.class, AppComponent.class
                );
    }

    @Override
    public boolean isActionable() {
        return true;
    }

    @Override
    public AppContextMenuPeer peer() {
        return (AppContextMenuPeer) super.peer();
    }

    public AppComponent createPreferredChild(String name, Path absolutePath) {
        return new Menu(app());
    }

    @Override
    public void show(AppComponent source, int x, int y) {
        app().toolkit().runUI(()-> {
            peer().show(source == null ? null : source.peer());
        });
    }
}

