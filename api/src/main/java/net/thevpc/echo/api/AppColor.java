package net.thevpc.echo.api;

import net.thevpc.echo.api.peers.AppColorPeer;

public interface AppColor {
    int r();
    int g();
    int b();
    int a();

    AppColorPeer peer();
}
