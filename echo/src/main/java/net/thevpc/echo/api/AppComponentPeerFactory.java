package net.thevpc.echo.api;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.spi.peers.AppComponentPeer;

public interface AppComponentPeerFactory {
    SupportSupplier<AppComponentPeer> createComponentPeer(AppComponent component);
}
