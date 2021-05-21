package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppControl;
import net.thevpc.echo.spi.peers.AppComponentPeer;

public class ControlBase extends ComponentBase implements AppControl {
    public ControlBase(String id, Application app,
                       Class<? extends AppComponent> componentType,
                       Class<? extends AppComponentPeer> peerType) {
        super(id, app, componentType, peerType);
    }
}
