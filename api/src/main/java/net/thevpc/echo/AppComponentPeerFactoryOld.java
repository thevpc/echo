package net.thevpc.echo;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.peers.AppComponentPeer;

import java.util.List;

public interface AppComponentPeerFactoryOld {
    List<Class> getSupportedComponentTypes();
    SupportSupplier<AppComponentPeer> createComponentPeer(AppComponent component);
}
