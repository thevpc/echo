package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.Path;
import net.thevpc.echo.api.components.AppBreadCrumb;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppToolBar;
import net.thevpc.echo.spi.peers.AppBreadCrumbPeer;
import net.thevpc.echo.spi.peers.AppToolBarPeer;

public class BreadCrumb extends ButtonGroupBase implements AppToolBar {
    public BreadCrumb(String id, Str text, Application app) {
        super(id, text, app, AppBreadCrumb.class, AppBreadCrumbPeer.class);
    }

    public BreadCrumb(Application app) {
        this(null, null, app);
    }

    @Override
    public AppComponent createPreferredChild(String name, Path absolutePath) {
        return new Button(app()).with((Menu m)->m.text().set(Str.i18n(absolutePath.toString())));
    }

}

