package net.thevpc.echo.impl.components;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppControl;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.api.tools.AppComponentModel;

public class AppControlBase extends AppComponentBase implements AppControl {
    public AppControlBase(AppComponentModel tool,
                          Class<? extends AppComponentModel> modelType,
                          Class<? extends AppComponent> componentType,
                          Class<?  extends AppComponentPeer> peerType) {
        super(tool, modelType, componentType, peerType);
    }
}
