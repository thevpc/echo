package net.thevpc.echo;

import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.spi.peers.AppColorPeer;

public class DefaultColor implements AppColor {
    public DefaultColor() {
    }

    @Override
    public double red() {
        return 0;
    }

    @Override
    public double green() {
        return 0;
    }

    @Override
    public double blue() {
        return 0;
    }

    @Override
    public double opacity() {
        return 0;
    }

    @Override
    public int red255() {
        return 0;
    }

    @Override
    public int green255() {
        return 0;
    }

    @Override
    public int blue255() {
        return 0;
    }

    @Override
    public int opacity255() {
        return 0;
    }

    @Override
    public AppColorPeer peer() {
        return null;
    }

    @Override
    public int rgba() {
        return 0;
    }

    @Override
    public String format() {
        return "<default>";
    }

    @Override
    public String toString() {
        return format();
    }
}
