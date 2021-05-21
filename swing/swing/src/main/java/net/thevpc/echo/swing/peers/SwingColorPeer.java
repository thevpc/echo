package net.thevpc.echo.swing.peers;

import net.thevpc.echo.spi.peers.AppColorPeer;

import java.awt.*;

public class SwingColorPeer implements AppColorPeer {
    private Color awtColor;

    public SwingColorPeer(Color awtColor) {
        this.awtColor = awtColor;
    }

    @Override
    public Object toolkitColor() {
        return awtColor;
    }
}
