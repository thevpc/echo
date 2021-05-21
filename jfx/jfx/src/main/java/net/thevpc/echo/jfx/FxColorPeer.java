package net.thevpc.echo.jfx;

import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.spi.peers.AppColorPeer;

public class FxColorPeer implements AppColorPeer {
    private final AppColor c;
    javafx.scene.paint.Color toolkitColor;

    public FxColorPeer(AppColor c) {
        this.c = c;
        toolkitColor = new javafx.scene.paint.Color(
                c.red(), c.green(), c.blue(), c.opacity()
        );
    }

    @Override
    public Object toolkitColor() {
        return toolkitColor;
    }
}
