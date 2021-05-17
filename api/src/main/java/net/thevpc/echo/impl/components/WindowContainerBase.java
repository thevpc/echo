package net.thevpc.echo.impl.components;

import net.thevpc.echo.api.components.AppContainer;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.api.components.AppWindowContainer;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.api.peers.AppWindowPeer;
import net.thevpc.echo.api.tools.AppContainerModel;
import net.thevpc.echo.api.tools.AppWindowModel;

public class WindowContainerBase extends AppContainerBase<AppWindowModel, AppWindow> implements AppWindowContainer {
    public WindowContainerBase(AppContainerModel folder,
                               Class<? extends AppContainerModel> modelType,
                               Class<? extends AppWindowContainer> componentType,
                               Class<? extends AppComponentPeer> peerType) {
        super(folder,
                modelType, componentType, peerType, AppWindowModel.class, AppWindow.class);
    }

}

