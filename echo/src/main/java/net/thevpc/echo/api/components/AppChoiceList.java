package net.thevpc.echo.api.components;

import net.thevpc.echo.spi.peers.AppChoiceListPeer;

public interface AppChoiceList<T> extends AppChoiceControl<T> {

    AppChoiceListPeer peer();

    void ensureIndexIsVisible(int index);
}
