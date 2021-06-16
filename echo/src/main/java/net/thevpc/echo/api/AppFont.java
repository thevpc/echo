package net.thevpc.echo.api;

import net.thevpc.echo.Font;
import net.thevpc.echo.FontPosture;
import net.thevpc.echo.FontWeight;
import net.thevpc.echo.spi.peers.AppFontPeer;

public interface AppFont {
    AppFontPeer peer();

    Font derive(FontPosture posture);

    Font derive(FontWeight weight);

    Font derive(double size);

    Font derive(String name);

    Font derive(String name, Double size, FontWeight weight, FontPosture posture);

    String family();

    FontWeight weight();

    FontPosture posture();

    double size();

    String format();
}
