package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.Path;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppToolBarGroup;
import net.thevpc.echo.spi.peers.AppToolBarGroupPeer;

public class ToolBarGroup extends ButtonGroupBase implements AppToolBarGroup {
    public ToolBarGroup(String id, Str text, Application app) {
        super(id, text, app, AppToolBarGroup.class, AppToolBarGroupPeer.class);
    }

    public ToolBarGroup(Application app) {
        this(null, null, app);
    }
    @Override
    public AppComponent createPreferredChild(String name, Path absolutePath) {
        return new ToolBar(app());
    }

}

