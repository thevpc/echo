package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.Path;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppMenu;
import net.thevpc.echo.spi.peers.AppMenuPeer;

public class Menu extends ButtonGroupBase implements AppMenu {
    public Menu(Str text, Application app) {
        this(null,text,app);
    }
    public Menu(String id, Str text, Application app) {
        super(id, text, app, AppMenu.class, AppMenuPeer.class);
    }

    public Menu(Application app) {
        this(null, null, app);
    }

    @Override
    public void show(Object source, int x, int y) {

    }

    @Override
    public AppComponent createPreferredChild(String name, Path absolutePath) {
        return new Menu(app()).with((Menu m) -> m.text().set(Str.i18n(absolutePath.toString())));
    }
}

