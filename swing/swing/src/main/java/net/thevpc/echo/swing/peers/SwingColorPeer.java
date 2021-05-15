package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.peers.AppColorPeer;

import java.awt.*;

public class SwingColorPeer implements AppColorPeer {
    private Color awtColor;

    public SwingColorPeer(int rgba, boolean useAlpha) {
        this(new Color(rgba, useAlpha));
    }

    public SwingColorPeer(int r, int g,int b,int a) {
        this(new Color(r,g, b,a));
    }

    public SwingColorPeer(float r, float g,float b,float a) {
        this(new Color(r,g, b,a));
    }

    public SwingColorPeer(Color awtColor) {
        this.awtColor = awtColor;
    }

    @Override
    public Object toolkitColor() {
        return awtColor;
    }

    @Override
    public int r() {
        return awtColor.getRed();
    }

    @Override
    public int g() {
        return awtColor.getGreen();
    }

    @Override
    public int b() {
        return awtColor.getBlue();
    }

    @Override
    public int a() {
        return awtColor.getAlpha();
    }
}
