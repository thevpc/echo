package net.thevpc.echo.api;

import net.thevpc.echo.spi.peers.AppColorPeer;

public interface AppColor extends AppPaint{
    double red();

    double green();

    double blue();

    double opacity();

    int red255();

    int green255();

    int blue255();

    int opacity255();

    AppColorPeer peer();

    int rgba();

}
