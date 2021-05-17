package net.thevpc.echo.api.components;


import net.thevpc.echo.AppDimension;
import net.thevpc.echo.api.peers.AppDesktopPeer;

public interface AppDesktop extends AppWindowContainer {
    AppDimension size();

    void tileDesktop(boolean vertical);

    void iconDesktop(boolean iconify);

    void closeAllDesktop();

    AppDesktopPeer peer();

    AppDesktopPeer peer(boolean prepareShowing);
}
