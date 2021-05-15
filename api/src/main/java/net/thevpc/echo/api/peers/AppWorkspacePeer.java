package net.thevpc.echo.api.peers;

import net.thevpc.echo.AppWindowAnchor;
import net.thevpc.echo.api.components.AppComponent;

public interface AppWorkspacePeer extends AppComponentPeer{
    boolean dockingSupported() ;

    boolean desktopEnabled() ;

    void tileDesktop(boolean vertical) ;

    void iconDesktop(boolean iconify) ;

     void closeAllDesktop() ;

    AppWindowPeer addWindowImpl(String id, AppComponent component, AppWindowAnchor anchor) ;

    void removeWindowImpl(String id, AppWindowPeer a) ;

}
