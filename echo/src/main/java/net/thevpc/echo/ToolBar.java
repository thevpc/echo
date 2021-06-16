package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.Path;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppToolBar;
import net.thevpc.echo.spi.peers.AppToolBarPeer;

public class ToolBar extends ButtonGroupBase implements AppToolBar {
    public ToolBar(String id, Str text, Application app) {
        super(id, text, app, AppToolBar.class, AppToolBarPeer.class);
    }

    public ToolBar(Application app) {
        this(null, null, app);
    }

    @Override
    public AppComponent createPreferredChild(String name, Path absolutePath) {
        return new Menu(app()).with((Menu m)->m.text().set(Str.i18n(absolutePath.toString())));
    }

}

