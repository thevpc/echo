package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.impl.tools.AppToolBase;
import net.thevpc.echo.api.peers.AppComponentPeer;

public class CustomComponent extends AppComponentBase {
    public CustomComponent(Application app, AppComponentPeer peer) {
        super(new AppToolBase(app));
        this.peer=peer;
    }

}

