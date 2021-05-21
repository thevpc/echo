package net.thevpc.echo.spi.peers;

import net.thevpc.echo.Bounds;

public interface AppWindowPeer extends AppComponentPeer{

    void resize(double x,double y,double w,double h);

    Bounds bounds();
}
