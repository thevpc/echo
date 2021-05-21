package net.thevpc.echo.api.components;


import net.thevpc.echo.Dimension;
import net.thevpc.echo.spi.peers.AppDesktopPeer;

public interface AppDesktop extends AppWindowContainer {
    Dimension size();

    void tileDesktop(boolean vertical);

    void iconDesktop(boolean iconify);

    void closeAllDesktop();

    AppDesktopPeer peer();

    AppDesktopPeer peer(boolean prepareShowing);
}
