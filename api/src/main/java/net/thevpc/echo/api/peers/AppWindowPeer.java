package net.thevpc.echo.api.peers;

import net.thevpc.echo.AppBounds;

public interface AppWindowPeer extends AppComponentPeer{

    void resize(double x,double y,double w,double h);

    AppBounds bounds();
}
