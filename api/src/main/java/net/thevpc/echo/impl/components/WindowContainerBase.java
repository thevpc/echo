package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.api.components.AppWindowContainer;
import net.thevpc.echo.spi.peers.AppComponentPeer;

public class WindowContainerBase extends ContainerBase<AppWindow> implements AppWindowContainer {
    public WindowContainerBase(String id, Application app,
                               Class<? extends AppWindowContainer> componentType,
                               Class<? extends AppComponentPeer> peerType) {
        super(id, app, componentType, peerType, AppWindow.class);
    }

}

