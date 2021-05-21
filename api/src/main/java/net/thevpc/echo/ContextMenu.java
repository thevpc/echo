package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.Path;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppContextMenu;
import net.thevpc.echo.spi.peers.AppContextMenuPeer;

public class ContextMenu extends ButtonGroupBase
        implements AppContextMenu {
    public ContextMenu(String id, Str text, Application app) {
        super(id, text, app, AppContextMenu.class, AppContextMenuPeer.class);
    }

    public ContextMenu(Application app) {
        this(null, null, app);
    }

    @Override
    public AppContextMenuPeer peer() {
        return (AppContextMenuPeer) super.peer();
    }

    public AppComponent createPreferredChild(String name, Path absolutePath) {
        return new Menu(app()).with((Menu m)->m.text().set(Str.i18n(absolutePath.toString())));
    }

    @Override
    public void show(AppComponent source, int x, int y) {
        app().toolkit().runUI(()-> {
            peer().show(source == null ? null : source.peer());
        });
    }
}

