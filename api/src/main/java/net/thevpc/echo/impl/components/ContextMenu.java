package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Path;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppContextMenu;
import net.thevpc.echo.api.components.AppMenu;
import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.api.tools.AppToolFolder;
import net.thevpc.echo.api.peers.AppContextMenuPeer;

public class ContextMenu extends AppContainerBase<AppComponent, AppTool> implements AppContextMenu {
    public ContextMenu(AppToolFolder tool) {
        super(tool,AppComponent.class, AppTool.class);
    }

    @Override
    public boolean isActionable() {
        return true;
    }

    @Override
    public AppContextMenuPeer peer() {
        return (AppContextMenuPeer) super.peer();
    }

    @Override
    public AppComponent createPreferredComponent(AppTool tool, String name, Path absolutePath, AppComponentOptions options) {
        if(options.componentType()==null && tool instanceof AppToolFolder){
            options.componentType(AppMenu.class);
        }
        return super.createPreferredComponent(tool, name, absolutePath, options);
    }

    @Override
    public void show(AppComponent source, int x, int y) {
        peer().show(source==null?null:source.peer());
    }
}

