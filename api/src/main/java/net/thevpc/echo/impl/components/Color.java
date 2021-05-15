package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.peers.AppColorPeer;

public class Color implements AppColor {
    private AppColorPeer peer;

    private Application app;

    public Color(int rgba, boolean hasAlpha,Application app) {
        this.app = app;
        peer = app.toolkit().createColorPeer(rgba, hasAlpha);
    }

    @Override
    public int r() {
        return peer.r();
    }

    @Override
    public int g() {
        return peer.g();
    }

    @Override
    public int b() {
        return peer.b();
    }

    @Override
    public int a() {
        return peer.a();
    }
}
