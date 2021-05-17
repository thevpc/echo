package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.peers.AppColorPeer;

import java.beans.ConstructorProperties;

public class Color implements AppColor {
    public static Color BLACK(Application app){
        return new Color(0, 0, 0,app);
    }
    public static Color WHITE(Application app){
        return new Color(255, 255, 255,app);
    }
    private AppColorPeer peer;

    private Application app;

    public Color(int rgba, boolean hasAlpha,Application app) {
        this.app = app;
        peer = app.toolkit().createColorPeer(rgba, hasAlpha);
    }
    public Color(int r, int g, int b,Application app) {
        this(r, g, b, 255,app);
    }

    public Color(int r, int g, int b, int a,Application app) {
        this(((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                ((b & 0xFF) << 0),true,app);
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

    @Override
    public AppColorPeer peer() {
        return peer;
    }
}
